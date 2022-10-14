package sit.int221.oasipserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class OasipServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(OasipServerApplication.class, args);
    }

//    @PostConstruct
//    public void init(){
//        // Setting Spring Boot SetTimeZone
//        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
//    }

}
