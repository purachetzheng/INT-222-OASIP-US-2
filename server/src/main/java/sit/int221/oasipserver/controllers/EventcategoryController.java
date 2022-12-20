package sit.int221.oasipserver.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasipserver.dtos.eventCategory.EventcategoryDetailDto;
import sit.int221.oasipserver.dtos.eventCategory.EventcategoryDto;
import sit.int221.oasipserver.dtos.eventCategory.PageEventCategoryDto;
import sit.int221.oasipserver.dtos.eventCategory.CategoryOwnerDto;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.entities.EventcategoryOwner;
import sit.int221.oasipserver.repo.CategoryOwnerRepository;
import sit.int221.oasipserver.repo.EventcategoryRepository;
import sit.int221.oasipserver.services.EventcategoryService;
import sit.int221.oasipserver.utils.ListMapper;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/eventcategories")
public class EventcategoryController {
    @Autowired
    private EventcategoryService eventcategoryService;
    @Autowired
    CategoryOwnerRepository categoryOwnerRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    EventcategoryRepository repository;
    @Autowired
    ListMapper listMapper;

    //    @GetMapping("")
//    public List<EventcategoryDto> getAllEventcategories(){
//        return eventcategoryService.getAll();
//    }
    @GetMapping("")
    public PageEventCategoryDto getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
            ) {
        return eventcategoryService.getAllPage(page, pageSize);
    }

    @GetMapping("{id}")
    public EventcategoryDetailDto getEventcategoryById(@PathVariable Integer id) {
        return modelMapper.map(eventcategoryService.getById(id), EventcategoryDetailDto.class);
    }

    @GetMapping("/categoryOwners")
    public List<CategoryOwnerDto> categoryOwners() {
        List<EventcategoryOwner> categoryOwnerList = categoryOwnerRepository.findAll();
        for(EventcategoryOwner z : categoryOwnerList){
            System.out.println(z.getId());
        }

        return listMapper.mapList(categoryOwnerList, CategoryOwnerDto.class, modelMapper);
    }

    @PostMapping("")
    public Eventcategory createEventcategory(@RequestBody EventcategoryDto newEventcategory) {
        return eventcategoryService.create(newEventcategory);
    }

    @DeleteMapping("/{id}")
    public void deleteEvent(@PathVariable Integer id) {
        eventcategoryService.delete(id);
    }

    @PutMapping("/{id}")
    public EventcategoryDetailDto updateEventcategory(
            @Valid @RequestBody EventcategoryDto eventcategoryDto,
            @PathVariable Integer id) {
        return eventcategoryService.update(eventcategoryDto, id);
    }
}
