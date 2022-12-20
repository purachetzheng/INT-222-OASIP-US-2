package sit.int221.oasipserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int221.oasipserver.entities.Event;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.entities.User;

import java.util.List;
import java.util.Set;

public interface EventcategoryRepository extends JpaRepository<Eventcategory, Integer> {
//    public Eventcategory findByEventCategoryName();
    public Eventcategory findEventcategoryByUsersId(Integer id);
    public List<Eventcategory> findAllByUsersId(Integer id);

    public Eventcategory findEventcategoryById(Integer id);

    @Query( value = "SELECT COUNT(*) FROM eventcategoryowner where eventCategoryId = ?1"
            ,nativeQuery = true)
    public Integer countCategoryUsers(Integer categoryId);

    @Query( value = "SELECT * FROM eventcategoryowner"
            ,nativeQuery = true)
    public List<?> categoryOwners();


    public List<Eventcategory> findEventcategoriesByUsersLike(User user);



}