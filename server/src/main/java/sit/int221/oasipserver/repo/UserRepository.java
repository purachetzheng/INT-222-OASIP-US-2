package sit.int221.oasipserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.int221.oasipserver.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAllByOrderByNameAsc();

    public boolean existsByName(String name);

    public boolean existsByEmail(String email);

    public boolean existsByNameAndIdNot(String name, Integer id);

    public boolean existsByEmailAndIdNot(String name, Integer id);

    public User findByEmail(String email);

//    @Query(value = "INSERT INTO users")
//    void insertPassword(@Param("password") String password);
}
