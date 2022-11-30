package sit.int221.oasipserver.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int221.oasipserver.dtos.eventCategory.EventcategoryDto;
import sit.int221.oasipserver.dtos.eventCategory.PageEventCategoryDto;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.entities.User;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.repo.EventcategoryRepository;
import sit.int221.oasipserver.repo.UserRepository;
import sit.int221.oasipserver.utils.ListMapper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class EventcategoryService {
    @Autowired
    private EventcategoryRepository repository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ListMapper listMapper;
    @Autowired private UserRepository userRepository;

    public List<EventcategoryDto> getAll () {
        List<Eventcategory> eventcategoryList = repository.findAll();
        return listMapper.mapList(eventcategoryList, EventcategoryDto.class, modelMapper);
    }
    public PageEventCategoryDto getAllPage(int pageNum, int pageSize) {
//        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageRequest = PageRequest.of(pageNum, pageSize);
//        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        String currentPrincipalName = getCurrentAuthentication.getUsername();
//        Collection currentPrincipalRole = getCurrentAuthentication.getAuthorities();
//        System.out.println(currentPrincipalRole + "\n" + currentPrincipalName);
        Page page = repository.findAll(pageRequest);
        PageEventCategoryDto pageEventCategoryDto = modelMapper.map(page, PageEventCategoryDto.class);
        return pageEventCategoryDto;
    }


    public Eventcategory getById (Integer id) {
        Eventcategory eventcategory = repository.findById(id).orElseThrow
                (() -> new ApiNotFoundException("Category id " + id + " Does Not Exist !!!"));
        return eventcategory;
    }

    public Eventcategory create(EventcategoryDto newEventcategory){
        Eventcategory eventcategory = modelMapper.map(newEventcategory, Eventcategory.class);
        return repository.saveAndFlush(eventcategory);
    }

    public void delete(Integer id){
        repository.findById(id).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,
                        id + " does not exist !!!"));
        repository.deleteById(id);
    }

    public EventcategoryDto update(EventcategoryDto eventcategoryDto, Integer id) {
        Eventcategory eventcategory = mapEventcategory(getById(id), eventcategoryDto);
        if(eventcategoryDto.getUserOwners() != null) {
            mapCategoryOwner(eventcategoryDto, eventcategory);
        }
        return modelMapper.map(repository.saveAndFlush(eventcategory),EventcategoryDto.class);
    }

    private Eventcategory mapEventcategory(Eventcategory eventcategory, EventcategoryDto eventcategoryDto) {
        eventcategory.setEventCategoryName(eventcategoryDto.getEventCategoryName());
        eventcategory.setEventCategoryDescription(eventcategoryDto.getEventCategoryDescription());
        eventcategory.setEventDuration(eventcategoryDto.getEventDuration());
        return eventcategory;
    }

    @PreAuthorize("hasRole('ROLE_admin')")
    private Eventcategory mapCategoryOwner(EventcategoryDto updateCategory, Eventcategory eventcategory) {
        Set<User> userCategoriesOwner = new HashSet<>(); //สร้าง Set ไว้สำหรับ patch เข้าไป DB
        if(updateCategory.getUserOwners() != null) { //ถ้ามีส่งมา
            for(Integer userId : updateCategory.getUserOwners()) { //forEach แต่ละ id ที่ส่งมา
                System.out.println("This is id: " + userId + " to patched");
                userCategoriesOwner.add(userRepository.findById(userId).orElseThrow()); //add object แต่ละ id เข้าไปใน Set
            }
            eventcategory.setUsers(userCategoriesOwner); //set object ว่า category นี้มี user ไหนบ้าง (ของ Lecturer)
            for(User user : eventcategory.getUsers()) {
                System.out.println(user.getId() + ": " + user.getEmail());
            }
        }
        return eventcategory;
    }

}
