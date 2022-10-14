package sit.int221.oasipserver.services;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpServerErrorException;
import sit.int221.oasipserver.dtos.event.*;
import sit.int221.oasipserver.email.EmailServiceImpl;
import sit.int221.oasipserver.entities.Event;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.exception.ForbiddenException;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.exception.type.ApiRequestException;
import sit.int221.oasipserver.repo.EventRepository;
import sit.int221.oasipserver.repo.EventcategoryRepository;
import sit.int221.oasipserver.repo.UserRepository;
import sit.int221.oasipserver.utils.ListMapper;
import sit.int221.oasipserver.utils.OverlapValidate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

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
    @Autowired
    EventcategoryRepository eventcategoryRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    EmailServiceImpl emailService;

    final private FieldError overlapErrorObj = new FieldError("newEventDto",
            "eventStartTime", "overlapped with other events");

    final private FieldError bookingEmailNotMatchObj = new FieldError("newEventDto",
            "bookingEmail", "The booking email must be the same as the student's email");

    public List<SimpleEventDto> getAll() {
        List<Event> eventList = repository.findAllByOrderByEventStartTimeDesc();
//        List<Event> eventList = repository.findAllByEventCategoryId(1);
//        List<Event> eventList = repository.findAllByEventCategoryEventCategoryName("Project Management Clinic");
        return listMapper.mapList(eventList, SimpleEventDto.class, modelMapper);
    }


    public PageEventDto getEventPage(int pageNum, int pageSize, String sortBy, Integer eventCategoryId, String dateStatus, String date) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageRequest = PageRequest.of(pageNum, pageSize, sort);
//        return modelMapper.map(repository.findAllByEventStartTimePast(pageRequest), PageEventDto.class);
        Page<Event>  page = filterEventPage(pageRequest, eventCategoryId, dateStatus, date);
        PageEventDto pageDto = modelMapper.map(page, PageEventDto.class);
        System.out.println(getCurrentAuthority());
        System.out.println(getCurrentAuthorityCollection());
        return pageDto;
    }

    private Page<Event> filterEventPage(Pageable pageRequest, Integer eventCategoryId, String dateStatus, String date) {
        UserDetails currentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentPrincipalEmail = currentAuthentication.getUsername();
        if(dateStatus.equals("past"))
            return repository.findAllEventPast(pageRequest, eventCategoryId, date);
        if(dateStatus.equals("upcoming"))
            return repository.findAllEventUpcoming(pageRequest, eventCategoryId, date);
        if(date == null && eventCategoryId == null && getCurrentAuthority().equals("[ROLE_student]")){
            System.out.println("student paging");
            return repository.findByBookingEmail(pageRequest, currentPrincipalEmail);
        } else if(date == null && eventCategoryId == null && getCurrentAuthority().equals("[ROLE_admin]")) {
            System.out.println("admin paging");
            return repository.findAll(pageRequest);
        } else if(date == null && eventCategoryId == null && getCurrentAuthority().equals("[ROLE_lecturer]")) {
            System.out.println("lecturer paging");
            Integer userId = userRepository.findUserIdByEmail(currentPrincipalEmail);
            System.out.println(userId);
            Eventcategory categoryIdOwner = eventcategoryRepository.findEventcategoryByUsersId(userId);
            System.out.println(categoryIdOwner.getId());
            return repository.findByEventCategoryId(pageRequest, categoryIdOwner.getId());
//            return repository.findByEventCategory(eventcategoryownerRepository.findById(pageRequest, 1));
        }


        return repository.findAllFilter(pageRequest, eventCategoryId, date);
    }

    @PreAuthorize("hasRole('ROLE_student') or hasRole('ROLE_admin') or hasRole('ROLE_lecturer')")
    public Event getById(Integer id, HttpServletResponse response) throws ForbiddenException {
        Event event = repository.findById(id).orElseThrow
                (() -> new ApiNotFoundException("Event id " + id + " Does Not Exist !!!"));

//        if(!getCurrentUserPrincipalEmail().equals(event.getBookingEmail())){
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            return null;
//        }

        if(getCurrentAuthority().equals("[ROLE_student]")){
            if(!getCurrentUserPrincipalEmail().equals(event.getBookingEmail())){
//                response.setStatus(HttpStatus.FORBIDDEN.value());
//                System.out.println("403");
                throw new ForbiddenException();
            }
        } else if(getCurrentAuthority().equals("[ROLE_lecturer]")){
            Integer userId = userRepository.findUserIdByEmail(getCurrentUserPrincipalEmail());
            System.out.println(userId);
            Eventcategory categoryIdOwner = eventcategoryRepository.findEventcategoryByUsersId(userId);
            System.out.println(categoryIdOwner.getId());
            if(!categoryIdOwner.getId().equals(event.getEventCategory().getId())){
                throw new ForbiddenException();
            }
        }
        return event;
    }

    @PreAuthorize("hasRole('ROLE_student') or hasRole('ROLE_admin')")
    public void delete(Integer id, HttpServletResponse response) throws ForbiddenException {

        Event event = repository.findById(id).orElseThrow
                (() -> new ApiNotFoundException("Event id " + id + " Does Not Exist !!!"));
        if(getCurrentAuthority().equals("[ROLE_student]")){
            if(!getCurrentUserPrincipalEmail().equals(event.getBookingEmail())){
                throw new ForbiddenException();
            }
        }

        repository.delete(getById(id, response));
    }

    public SimpleEventDto create(PostEventDto newEvent, BindingResult result, TimeZone timeZone)
            throws MethodArgumentNotValidException, ApiRequestException {

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

        if(getCurrentAuthority().equals("[ROLE_student]")){
            if(!newEvent.getBookingEmail().equals(getCurrentUserPrincipalEmail())){
                result.addError(bookingEmailNotMatchObj);
            }
        }

        if (result.hasErrors()) throw new MethodArgumentNotValidException(null, result);

        emailService.sendSimpleMessage(newEvent, timeZone);



        return modelMapper.map(repository.saveAndFlush(event), SimpleEventDto.class);
    }

    @PreAuthorize("hasRole('ROLE_student') or hasRole('ROLE_admin')")
    public EventDto update(PatchEventDto updateEventDto, Integer id, BindingResult result, HttpServletResponse response)
            throws MethodArgumentNotValidException, ForbiddenException {
        Event eventForEmailCheck = repository.findById(id).orElseThrow
                (() -> new ApiNotFoundException("Event id " + id + " Does Not Exist !!!"));
        if(getCurrentAuthority().equals("[ROLE_student]")){
            if(!getCurrentUserPrincipalEmail().equals(eventForEmailCheck.getBookingEmail())){
                throw new ForbiddenException();
            }
        }

        Event event = mapEvent(getById(id, response), updateEventDto);

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

        return modelMapper.map(repository.saveAndFlush(event), EventDto.class);
    }

    private Event mapEvent(Event existingEvent, PatchEventDto updateEvent) {
        if (updateEvent.getEventStartTime() != null)
            existingEvent.setEventStartTime(updateEvent.getEventStartTime());
        if (updateEvent.getEventNotes() != null)
            existingEvent.setEventNotes(updateEvent.getEventNotes());
        return existingEvent;
    }

    private String getCurrentUserPrincipalEmail(){
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getCurrentAuthentication.getUsername();
    }

    private String getCurrentAuthority(){
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  getCurrentAuthentication.getAuthorities().toString();
    }

    private Collection<?> getCurrentAuthorityCollection(){
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  getCurrentAuthentication.getAuthorities();
    }

}
