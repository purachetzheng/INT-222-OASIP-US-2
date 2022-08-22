package sit.int221.oasipserver.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasipserver.dtos.event.*;
import sit.int221.oasipserver.services.EventService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired public EventService eventService;
    @Autowired private ModelMapper modelMapper;

//    @GetMapping("")
//    public List<SimpleEventDto> getAllEvent(){
//        return eventService.getAll();
//    }

    @GetMapping("")
    public PageEventDto getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(required = false) Integer eventCategoryID,
            @RequestParam(required = false) String dateStatus,
            @RequestParam(required = false) String date
    ) {
        return eventService.getEventPage(page, pageSize, sortBy, eventCategoryID, dateStatus, date);
    }
    @GetMapping("/{id}")
    public EventDto getEventById(@PathVariable Integer id){
        return modelMapper.map(eventService.getById(id), EventDto.class);
    }

    @PostMapping("")
    public SimpleEventDto createEvent(
            @Valid @RequestBody PostEventDto newEvent,
            BindingResult result)
            throws MethodArgumentNotValidException{
        return eventService.create(newEvent, result);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        eventService.delete(id);
    }

    @PatchMapping("/{id}")
    public EventDto updateEvent(
            @Valid @RequestBody PatchEventDto updateEventDto,
            @PathVariable Integer id,
            BindingResult result)
            throws MethodArgumentNotValidException{
        return eventService.update(updateEventDto, id, result);
    }

}
