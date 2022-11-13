package sit.int221.oasipserver.file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;


@Service
public class FilesStorageServiceImpl implements FilesStorageService {

    private final Path root = Paths.get("uploads");

    @Override
    public void init() {
        try {
            Files.createDirectory(root);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload!");
        }
    }

//    @Override
//    public void save(MultipartFile file) {
//        try {
//            Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
//        } catch (Exception e) {
//            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
//        }
//    }

    @Override
    public String save(MultipartFile file) throws IOException {
        String uuidDir = UUID.randomUUID().toString(); //UUID
        File dir = new File(String.valueOf(root), uuidDir); //Create Dir with UUID name
        Files.createDirectories(dir.toPath().toAbsolutePath()); //Create Dir with UUID name
        File destination = new File(dir, file.getOriginalFilename()); //Save file uploaded to Dir
        System.out.println("Saving to " + destination.getAbsolutePath()); //Save file uploaded to Dir
        file.transferTo(new File(destination.getAbsolutePath()));
        return uuidDir;
    }

    @Override
    public Optional<File> load(String filename) {
        File dir = new File(String.valueOf(root), filename); //Find Dir by UUID name
        File[] files = dir.listFiles(); //List files in Dir
        if (files != null) {
            return Optional.of(files[0]); //ได้ File Object
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }



    @Override
    public void delete(String filename){
        try{
            FileSystemUtils.deleteRecursively(root.resolve(filename));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
        } catch (IOException e) {
            throw new RuntimeException("Could not load the files!");
        }
    }
}
