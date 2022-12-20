package sit.int221.oasipserver.services;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import sit.int221.oasipserver.dtos.FileDto;
import sit.int221.oasipserver.dtos.event.*;
import sit.int221.oasipserver.email.EmailServiceImpl;
import sit.int221.oasipserver.entities.Event;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.entities.File;
import sit.int221.oasipserver.entities.User;
import sit.int221.oasipserver.exception.ForbiddenException;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.exception.type.ApiRequestException;
import sit.int221.oasipserver.file.FilesStorageServiceImpl;
import sit.int221.oasipserver.repo.EventRepository;
import sit.int221.oasipserver.repo.EventcategoryRepository;
import sit.int221.oasipserver.repo.FileRepository;
import sit.int221.oasipserver.repo.UserRepository;
import sit.int221.oasipserver.utils.ListMapper;
import sit.int221.oasipserver.utils.OverlapValidate;

import javax.servlet.http.HttpServletResponse;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.*;


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

    @Autowired
    FileRepository fileRepository;

    @Autowired
    FilesStorageServiceImpl storageService;

    private final Path root = Paths.get("uploads");

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

        String userEmail = currentAuthentication.getUsername();
        User user = userRepository.findByEmail(userEmail);
        String role = getCurrentAuthority();
        String studentEmail = role.equals("[ROLE_student]") ? userEmail : null;

        Set<Integer> lecturerCategoryIds = new HashSet<>();
        if(role.equals("[ROLE_lecturer]")){
            for(Eventcategory eventcategory : user.getCategoriesOwner()){
                lecturerCategoryIds.add(eventcategory.getId());
            }
            lecturerCategoryIds.stream().forEach(integer -> System.out.println(integer));
        }

        if(dateStatus.equals("past"))
            return repository.findAllEventPast(pageRequest, eventCategoryId, date, studentEmail, lecturerCategoryIds);

        if(dateStatus.equals("upcoming"))
            return repository.findAllEventUpcoming(pageRequest, eventCategoryId, date, studentEmail, lecturerCategoryIds);

        if(date == null && eventCategoryId == null)
            return repository.findAll(pageRequest, studentEmail, lecturerCategoryIds);

        return repository.findAllFilter(pageRequest, eventCategoryId, date, studentEmail, lecturerCategoryIds);
    }

    @PreAuthorize("hasRole('ROLE_student') or hasRole('ROLE_admin') or hasRole('ROLE_lecturer')")
    public Event getById(Integer id, HttpServletResponse response) throws ForbiddenException {
        Event event = repository.findById(id).orElseThrow
                (() -> new ApiNotFoundException("Event id " + id + " Does Not Exist !!!"));
        Boolean isStudent = getCurrentAuthority().equals("[ROLE_student]");
        Boolean isLecturer = getCurrentAuthority().equals("[ROLE_lecturer]");
        Boolean isAdmin = getCurrentAuthority().equals("[ROLE_admin]");
        Boolean isOwnerEvent = getCurrentUserPrincipalEmail().equals(event.getBookingEmail());

        if(isStudent && !isOwnerEvent){
//            response.setStatus(HttpStatus.FORBIDDEN.value());
//            System.out.println("403");
            throw new ForbiddenException();
        }
        if(isLecturer){
            Integer userId = userRepository.findUserIdByEmail(getCurrentUserPrincipalEmail());
            List<Eventcategory> categoriesOwner = eventcategoryRepository.findAllByUsersId(userId);
            Boolean isCategoryiesOwner = categoriesOwner.stream().anyMatch(
                    category -> category.getId().equals(event.getEventCategory().getId())
            );
            if(!isCategoryiesOwner) throw new ForbiddenException();
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

        if(event.getFile() != null) {
            storageService.delete(event.getFile().getId());
        }

        repository.delete(getById(id, response));
    }

    @PreAuthorize("hasRole('ROLE_student') or hasRole('ROLE_admin')")
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

        event.setEventCategory(category);

        if(newEvent.getFile() != null) {
            try {
                String uuid = storageService.save(newEvent.getFile()); //UUID
                File file = new File();
                file.setId(uuid);
                file.setFileName(newEvent.getFile().getOriginalFilename());
                file.setFileSize((int)newEvent.getFile().getSize());
                event.setFile(file); //DTO to DB
                String fileMsg = "Uploaded the file successfully: " + newEvent.getFile().getOriginalFilename();
                System.out.println(fileMsg);
            } catch (Exception e) {
                String fileMsg =  "Could not upload the file: " + newEvent.getFile().getOriginalFilename() + "!";
                System.out.println(fileMsg);
            }
        }

        if (result.hasErrors()) throw new MethodArgumentNotValidException(null, result);

        Thread t = new Thread(() -> {
            emailService.sendSimpleMessage(newEvent, timeZone);
        });
        t.start();

        return modelMapper.map(repository.saveAndFlush(event), SimpleEventDto.class);
    }

    public SimpleEventDto guestCreate(PostEventDto newEvent, BindingResult result, TimeZone timeZone)
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

        File fileNameExisting = eventForEmailCheck.getFile();


        if(updateEventDto.getFile() != null) {//มีไฟล์ส่งมา ซึ่งไม่ใช่ undefined
            System.out.println("เข้าเงื่อนไข");
            if(updateEventDto.getFile().isEmpty()) { //null or empty
                System.out.println("file params null");
                if(fileNameExisting != null) { //มีไฟล์ใน event อยู่ จึงลบออกเพราะ null ส่งมาแทน
                    System.out.println("มีไฟล์อยู่ ทำการลบ");
                    repository.updateFileToNull(eventForEmailCheck.getId());
                    fileRepository.deleteById(fileNameExisting.getId());
                    storageService.delete(fileNameExisting.getId());
                }
                System.out.println("set ไฟล์ใน event เป็น null");
                eventForEmailCheck.setFile(null);
            } else { //not null or not empty
                if(fileNameExisting != null) { //มีไฟล์ใน event อยู่จึงลบออกก่อนแล้วอัพอันใหม่
                    repository.updateFileToNull(eventForEmailCheck.getId());
                    fileRepository.deleteById(fileNameExisting.getId());
                    storageService.delete(fileNameExisting.getId());
                }
                upload(updateEventDto, eventForEmailCheck);
            }

        }


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
//        if (updateEvent.getFile() != null)
//            existingEvent.setFileName(updateEvent.getFile());
        return existingEvent;
    }

    private String getCurrentUserPrincipalEmail(){
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getCurrentAuthentication.getUsername();
    }

    private UserDetails getUserDetails(){
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    private String getCurrentAuthority(){
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  getCurrentAuthentication.getAuthorities().toString();
    }

    private Collection<?> getCurrentAuthorityCollection(){
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return  getCurrentAuthentication.getAuthorities();
    }

    private void upload(PatchEventDto updateEventDto, Event event) {
        try {
            String uuid = storageService.save(updateEventDto.getFile()); //UUID
            System.out.println("saved new file: " + uuid);
            File file = new File();
            file.setId(uuid);
            file.setFileName(updateEventDto.getFile().getOriginalFilename());
            file.setFileSize((int)updateEventDto.getFile().getSize());
            event.setFile(file); //DTO to DB
            String fileMsg = "Uploaded the file successfully: " + updateEventDto.getFile().getOriginalFilename();
            System.out.println(fileMsg);
        } catch (Exception e) {
            String fileMsg =  "Could not upload the file: " + updateEventDto.getFile().getOriginalFilename() + "!";
            System.out.println(fileMsg);
        }
    }

}

//        if(updateEventDto.getFile() != null) {
//            if(fileNameExisting != null) {
//                try {
//                    storageService.delete(fileNameExisting);
//                    System.out.println("deleted" + fileNameExisting);
//                    String uuid = storageService.save(updateEventDto.getFile()); //UUID
//                    System.out.println("saved new file: " + uuid);
//                    event.setFileName(uuid); //DTO to DB
//                    String fileMsg = "Uploaded the file successfully: " + updateEventDto.getFile().getOriginalFilename();
//                    System.out.println(fileMsg);
//                } catch (Exception e) {
//                    String fileMsg =  "Could not upload the file: " + updateEventDto.getFile().getOriginalFilename() + "!";
//                    System.out.println(fileMsg);
//                }
//            } else {
//                try {
//                    String uuid = storageService.save(updateEventDto.getFile()); //UUID
//                    System.out.println("saved new file: " + uuid);
//                    event.setFileName(uuid); //DTO to DB
//                    String fileMsg = "Uploaded the file successfully: " + updateEventDto.getFile().getOriginalFilename();
//                    System.out.println(fileMsg);
//                } catch (Exception e) {
//                    String fileMsg =  "Could not upload the file: " + updateEventDto.getFile().getOriginalFilename() + "!";
//                    System.out.println(fileMsg);
//                }
//            }
//        } else {
//            if(fileNameExisting != null) {
//                storageService.delete(fileNameExisting);
//            }
//        }
