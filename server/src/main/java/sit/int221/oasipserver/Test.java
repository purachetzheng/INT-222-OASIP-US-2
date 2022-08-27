package sit.int221.oasipserver;

import sit.int221.oasipserver.dtos.user.MatchUserDto;
import sit.int221.oasipserver.enums.UserRole;

import java.lang.reflect.Field;

public class Test {
    public static boolean findByName(String name) {
        for (UserRole role : UserRole.values()) {
            if(role.name().equals(name))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
//        System.out.println(findByName("admin2"));
    }
}
