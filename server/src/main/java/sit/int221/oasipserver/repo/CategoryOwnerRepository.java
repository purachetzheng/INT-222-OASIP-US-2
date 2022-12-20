package sit.int221.oasipserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sit.int221.oasipserver.entities.EventcategoryOwner;

import java.util.List;

public interface CategoryOwnerRepository extends JpaRepository<EventcategoryOwner, Integer> {
    @Query( value = "SELECT * FROM eventcategoryowner"
            ,nativeQuery = true)
    public List<?> categoryOwners();
}
