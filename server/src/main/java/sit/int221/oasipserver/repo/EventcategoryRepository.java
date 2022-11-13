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


    public List<Eventcategory> findEventcategoriesByUsersLike(User user);



}