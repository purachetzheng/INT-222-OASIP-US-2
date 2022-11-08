package sit.int221.oasipserver.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageService {
    public void init();

    public String save(MultipartFile file) throws IOException;

    public Optional<File> load(String filename);


    public void delete(String filename);
    public Stream<Path> loadAll();
}
