package sit.int221.oasipserver.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;
import sit.int221.oasipserver.dtos.event.PageEventDto;
import sit.int221.oasipserver.dtos.eventCategory.EventcategoryDto;
import sit.int221.oasipserver.dtos.eventCategory.PageEventCategoryDto;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.exception.type.ApiNotFoundException;
import sit.int221.oasipserver.repo.EventcategoryRepository;
import sit.int221.oasipserver.utils.ListMapper;

import java.util.Collection;
import java.util.List;

@Service
public class EventcategoryService {
    @Autowired
    private EventcategoryRepository repository;
    @Autowired private ModelMapper modelMapper;
    @Autowired private ListMapper listMapper;

    public List<EventcategoryDto> getAll () {
        List<Eventcategory> eventcategoryList = repository.findAll();
        return listMapper.mapList(eventcategoryList, EventcategoryDto.class, modelMapper);
    }
    public PageEventCategoryDto getAllPage(int pageNum, int pageSize) {
//        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);
        Pageable pageRequest = PageRequest.of(pageNum, pageSize);
        UserDetails getCurrentAuthentication = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String currentPrincipalName = getCurrentAuthentication.getUsername();
        Collection currentPrincipalRole = getCurrentAuthentication.getAuthorities();
        System.out.println(currentPrincipalRole + "\n" + currentPrincipalName);
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
        return modelMapper.map(repository.saveAndFlush(eventcategory),EventcategoryDto.class);
    }

    private Eventcategory mapEventcategory(Eventcategory eventcategory, EventcategoryDto eventcategoryDto) {
        eventcategory.setEventCategoryName(eventcategoryDto.getEventCategoryName());
        eventcategory.setEventCategoryDescription(eventcategoryDto.getEventCategoryDescription());
        eventcategory.setEventDuration(eventcategoryDto.getEventDuration());
        return eventcategory;
    }

}
