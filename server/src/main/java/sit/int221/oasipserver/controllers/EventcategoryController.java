package sit.int221.oasipserver.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sit.int221.oasipserver.dtos.eventCategory.EventcategoryDto;
import sit.int221.oasipserver.dtos.eventCategory.PageEventCategoryDto;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.services.EventcategoryService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/eventcategories")
public class EventcategoryController {
    @Autowired
    public EventcategoryService eventcategoryService;
    @Autowired
    private ModelMapper modelMapper;

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
    public EventcategoryDto getEventcategoryById(@PathVariable Integer id) {
        return modelMapper.map(eventcategoryService.getById(id), EventcategoryDto.class);
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
    public EventcategoryDto updateEventcategory(
            @Valid @RequestBody EventcategoryDto eventcategoryDto,
            @PathVariable Integer id) {
        return eventcategoryService.update(eventcategoryDto, id);
    }
}
