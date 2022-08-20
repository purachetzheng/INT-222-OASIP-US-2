package sit.int221.oasipserver;

import sit.int221.oasipserver.dtos.Role;

public class Test {
    public static boolean findByName(String name) {
        boolean result = true;
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(name)) {
                result = false;
                break;
            }
        }
        return result;
    }

    public static void main(String[] args) {

        System.out.println(findByName("studentadfasdfadsf"));
    }
}
