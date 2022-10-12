package sit.int221.oasipserver.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sit.int221.oasipserver.dtos.event.PostEventDto;
import sit.int221.oasipserver.dtos.event.SimpleEventDto;
import sit.int221.oasipserver.services.EventService;

import javax.validation.Valid;
import java.util.TimeZone;

@RestController
@RequestMapping("/api/guests")
public class GuestController {

    @Autowired
    public EventService eventService;

    @PostMapping("")
    public SimpleEventDto createEvent(
            @Valid @RequestBody PostEventDto newEvent,
            BindingResult result, TimeZone timeZone)
            throws MethodArgumentNotValidException {
        return eventService.create(newEvent, result, timeZone);
    }
}
