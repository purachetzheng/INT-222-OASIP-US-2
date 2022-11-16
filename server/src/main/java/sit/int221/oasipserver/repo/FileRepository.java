package sit.int221.oasipserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import sit.int221.oasipserver.entities.File;

public interface FileRepository extends JpaRepository<File, String> {

    @Modifying
    @Query(value = "DELETE FROM files WHERE fileID = ?1", nativeQuery = true)
    public void deleteById(String id);
}
