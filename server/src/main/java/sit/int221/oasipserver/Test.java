package sit.int221.oasipserver;

import sit.int221.oasipserver.enums.Role;

public class Test {
    public static boolean findByName(String name) {
        for (Role role : Role.values()) {
            if(role.name().equals(name))
                return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(findByName("admin2"));
    }
}
