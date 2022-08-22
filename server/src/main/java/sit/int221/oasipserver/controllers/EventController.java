package sit.int221.oasipserver.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasipserver.dtos.*;
import sit.int221.oasipserver.dtos.event.EventDto;
import sit.int221.oasipserver.dtos.event.EventPageDto;
import sit.int221.oasipserver.services.EventService;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired public EventService eventService;
    @Autowired private ModelMapper modelMapper;

    @GetMapping("")
    public List<EventDto> getAllEvent(){
        return eventService.getAll();
    }

    @GetMapping("/page")
    public EventPageDto getAllProducts(
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
    public EventDetailDto getEventById( @PathVariable Integer id){
        return modelMapper.map(eventService.getById(id), EventDetailDto.class);
    }

    @PostMapping("")
    public EventDto createEvent(
            @Valid @RequestBody CreateEventDto newEvent,
            BindingResult result)
            throws MethodArgumentNotValidException{
        return eventService.create(newEvent, result);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        eventService.delete(id);
    }

    @PatchMapping("/{id}")
    public EventDetailDto updateEvent(
            @Valid @RequestBody UpdateEventDto updateEventDto,
            @PathVariable Integer id,
            BindingResult result)
            throws MethodArgumentNotValidException{
        return eventService.update(updateEventDto, id, result);
    }

}
