package sit.int221.oasipserver.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasipserver.dtos.event.*;
import sit.int221.oasipserver.dtos.eventCategory.EventcategoryDto;
import sit.int221.oasipserver.email.EmailServiceImpl;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.exception.ForbiddenException;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.repo.EventcategoryRepository;
import sit.int221.oasipserver.repo.UserRepository;
import sit.int221.oasipserver.services.EventService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.TimeZone;

@RestController
@RequestMapping("/api/events")
@Component
public class EventController {
    @Autowired public EventService eventService;
    @Autowired private ModelMapper modelMapper;

    @Autowired private EmailServiceImpl emailService;

    @Autowired UserRepository userRepository;
    @Autowired private EventcategoryRepository eventcategoryRepository;

//    @GetMapping("")
//    public List<SimpleEventDto> getAllEvent(){
//        return eventService.getAll();
//    }


    @GetMapping("")
    public PageEventDto getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "eventStartTime") String sortBy,
            @RequestParam(required = false) Integer eventCategoryId,
            @RequestParam(defaultValue = "all") String dateStatus,
            @RequestParam(required = false) String date
    ) {
        return eventService.getEventPage(page, pageSize, sortBy, eventCategoryId, dateStatus, date);
    }


    @GetMapping("/{id}")
    public EventDto getEventById(@PathVariable Integer id, HttpServletResponse response) throws ForbiddenException {
        return modelMapper.map(eventService.getById(id, response), EventDto.class);
    }

//    @GetMapping("/{id}")
//    public EventDto getEventByIdTest(@PathVariable Integer id, HttpServletResponse response){
//        EventDto userDto = modelMapper.map(eventService.getById(id), EventDto.class);
//
//        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String currentPrincipalName = getCurrentAuthentication.getUsername();
//
//        if(userDto == null)
//            throw new ApiNotFoundException("Event id " + id + " Does Not Exist !!!");
//        if(!currentPrincipalName.equals(userDto.getBookingEmail())){
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            return null;
//        }
//
//
//        return userDto;
//    }

    @PostMapping("")
    public SimpleEventDto createEvent(
            @Valid @RequestBody PostEventDto newEvent,
            BindingResult result, TimeZone timeZone)
            throws MethodArgumentNotValidException{
        return eventService.create(newEvent, result, timeZone);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Integer id, HttpServletResponse response) throws ForbiddenException {
        eventService.delete(id, response);
    }

    @PatchMapping("/{id}")
    public EventDto updateEvent(
            @Valid @RequestBody PatchEventDto updateEventDto,
            @PathVariable Integer id,
            BindingResult result,
            HttpServletResponse response)
            throws MethodArgumentNotValidException, ForbiddenException{
        return eventService.update(updateEventDto, id, result, response);
    }

    @PostMapping("/emailSender")
    public PostEventDto sendEmail(@RequestBody PostEventDto eventDto, TimeZone timeZone){
        return emailService.sendSimpleMessage(eventDto, timeZone);
    }

    @GetMapping("/test")
    public EventcategoryDto test(){
        Integer id = userRepository.findUserIdByEmail("l@l.com");
        System.out.println(id);
        return modelMapper.map(eventcategoryRepository.findEventcategoryByUsersId(22), EventcategoryDto.class);
    }

    private String getCurrentUserPrincipalEmail(){
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getCurrentAuthentication.getUsername();
    }

}
