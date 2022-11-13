package sit.int221.oasipserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import sit.int221.oasipserver.file.FilesStorageService;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.TimeZone;

@SpringBootApplication
public class OasipServerApplication implements CommandLineRunner {
    @Resource
    FilesStorageService storageService;

    public static void main(String[] args) {
        SpringApplication.run(OasipServerApplication.class, args);
    }

    @Override
    public void run(String... arg) throws Exception {
        storageService.init();
    }

//    @PostConstruct
//    public void init(){
//        // Setting Spring Boot SetTimeZone
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//    }

}
