package sit.int221.oasipserver.services;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import sit.int221.oasipserver.dtos.*;
import sit.int221.oasipserver.dtos.event.EventDto;
import sit.int221.oasipserver.dtos.event.EventPageDto;
import sit.int221.oasipserver.entities.Event;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.repo.EventRepository;
import sit.int221.oasipserver.utils.ListMapper;
import sit.int221.oasipserver.utils.OverlapValidate;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class EventService {
    @Autowired
    private EventRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private ListMapper listMapper;
    @Autowired
    public EventcategoryService eventcategoryService;
    @Autowired
    private OverlapValidate overlapValidate;
    final private FieldError overlapErrorObj = new FieldError("newEventDto",
            "eventStartTime", "overlapped with other events");

    public List<EventDto> getAll() {
        List<Event> eventList = repository.findAllByOrderByEventStartTimeDesc();
//        List<Event> eventList = repository.findAllByEventCategoryId(1);
//        List<Event> eventList = repository.findAllByEventCategoryEventCategoryName("Project Management Clinic");
        return listMapper.mapList(eventList, EventDto.class, modelMapper);
    }

    public EventPageDto getEventPage(int pageNum, int pageSize, String sortBy, Integer eventCategoryID, String dateStatus, String date) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageRequest = PageRequest.of(pageNum, pageSize, sort);
        Page page = filterEventPage(pageRequest, eventCategoryID, dateStatus, date);
        EventPageDto pageDto = modelMapper.map(page, EventPageDto.class);
        System.out.println(dateStatus);
        System.out.println(eventCategoryID);
        System.out.println(date);
        return pageDto;
    }

    private Page filterEventPage(Pageable pageRequest, Integer eventCategoryID, String dateStatus, String date) {
        int x = 0;
        if(eventCategoryID != null) x += 1;
        if(dateStatus != null) x += 2;
        if(date != null) x += 4;

        switch(x) {
            case 1:
                return repository.findAllByEventCategoryId(pageRequest, eventCategoryID);
            case 2:
                if (dateStatus == "past") return repository.findAllByEventStartTimePast(pageRequest);
                if (dateStatus == "upcoming") return repository.findAllByEventStartTimeUpcoming(pageRequest);
            case 3:
                // code block
            case 4:
                return repository.findAllByEventStartTimeEquals(pageRequest, date);
            case 5:
                // code block
            case 6:
                // code block
            case 7:
                // code block
            default:
                return repository.findAll(pageRequest);
        }

//        if (eventCategoryID == null && dateStatus == null && date == null)
//            return repository.findAll(pageRequest);
//        if (dateStatus == null && date == null)
//            return repository.findAllByEventCategoryId(pageRequest, eventCategoryID);
//        if(eventCategoryID == null && date == null && dateStatus == "past")
//            return repository.findAllByEventStartTimePast(pageRequest);
//        if(eventCategoryID == null && date == null && dateStatus == "upcoming")
//            return repository.findAllByEventStartTimeUpcoming(pageRequest);
//        if(eventCategoryID == null && dateStatus == null)
//            return repository.findAllByEventStartTimeEquals(pageRequest, date);
//        return repository.findAll(pageRequest);
    }

    public Event getById(Integer id) {
        Event event = repository.findById(id).orElseThrow
                (() -> new ApiNotFoundException("Event id " + id + " Does Not Exist !!!"));
        return event;
    }

    public void delete(Integer id) {
        repository.delete(getById(id));
    }

    public EventDto create(CreateEventDto newEvent, BindingResult result)
            throws MethodArgumentNotValidException {

        if (result.hasErrors()
                && newEvent.getEventCategoryId() == null
                || newEvent.getEventStartTime() == null)
            throw new MethodArgumentNotValidException(null, result);

        Integer categoryId = newEvent.getEventCategoryId();
        Eventcategory category = eventcategoryService.getById(categoryId);
        newEvent.setEventDuration(category.getEventDuration());
        newEvent.setEventCategoryName(category.getEventCategoryName());

        Event event = modelMapper.map(newEvent, Event.class);
        ChronoUnit minutes = ChronoUnit.MINUTES;
        Integer duration = event.getEventDuration();

        List<Event> eventList = repository.findAllByEventCategoryIsAndEventStartTimeBetween
                (event.getEventCategory(), event.getEventStartTime().minus(480, minutes),
                        event.getEventStartTime().plus((480 + duration), minutes));

        if (overlapValidate.overlapCheck(event, eventList))
            result.addError(overlapErrorObj);

        if (result.hasErrors()) throw new MethodArgumentNotValidException(null, result);

        return modelMapper.map(repository.saveAndFlush(event), EventDto.class);
    }

    public EventDetailDto update(UpdateEventDto updateEventDto, Integer id, BindingResult result)
            throws MethodArgumentNotValidException {
        Event event = mapEvent(getById(id), updateEventDto);

        Eventcategory eventcategory = event.getEventCategory();
        ChronoUnit minutes = ChronoUnit.MINUTES;
        Integer duration = event.getEventDuration();
        //        List<Event> eventList = repository.
//                findAllByEventCategoryIsAndIdIsNot(eventcategory, id);
        List<Event> eventList = repository.
                findAllByEventCategoryIsAndIdIsNotAndEventStartTimeBetween(eventcategory, id,
                        event.getEventStartTime().minus(480, minutes),
                        event.getEventStartTime().plus((480 + duration), minutes));

        if (overlapValidate.overlapCheck(event, eventList))
            result.addError(overlapErrorObj);

        if (result.hasErrors()) throw new MethodArgumentNotValidException(null, result);

        return modelMapper.map(repository.saveAndFlush(event), EventDetailDto.class);
    }

    private Event mapEvent(Event existingEvent, UpdateEventDto updateEvent) {
        if (updateEvent.getEventStartTime() != null)
            existingEvent.setEventStartTime(updateEvent.getEventStartTime());
        if (updateEvent.getEventNotes() != null)
            existingEvent.setEventNotes(updateEvent.getEventNotes());
        return existingEvent;
    }

}
