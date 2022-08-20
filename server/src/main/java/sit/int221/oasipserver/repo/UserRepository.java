package sit.int221.oasipserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int221.oasipserver.entities.User;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    public List<User> findAllByOrderByNameAsc();

    public boolean existsByName(String name);

    public boolean existsByEmail(String email);

    public boolean existsByNameAndIdNot(String name, Integer id);

    public boolean existsByEmailAndIdNot(String name, Integer id);
}
