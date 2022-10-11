package sit.int221.oasipserver;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.springframework.beans.factory.annotation.Autowired;
import sit.int221.oasipserver.dtos.user.MatchUserDto;
import sit.int221.oasipserver.entities.Event;
import sit.int221.oasipserver.enums.UserRole;
import sit.int221.oasipserver.repo.EventRepository;
import sit.int221.oasipserver.repo.EventcategoryRepository;
import sit.int221.oasipserver.services.EventcategoryService;

import java.lang.reflect.Field;
import java.util.List;

public class Test {
    public static boolean findByName(String name) {
        for (UserRole role : UserRole.values()) {
            if(role.name().equals(name))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
//        EventcategoryService eventcategoryService = new EventcategoryService();



//        Argon2 argon2 = Argon2Factory.create();
//        String password = "admin";
//        String hash = argon2.hash(22, 65536, 1, password);
//        System.out.println(hash);
//
//        if(argon2.verify(hash, password)) {
//            System.out.println("Matched");
//        } else {
//            System.out.println("Not Matched");
//        }

    }
}
