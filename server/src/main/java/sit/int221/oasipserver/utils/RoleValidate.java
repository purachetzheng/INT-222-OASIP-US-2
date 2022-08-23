package sit.int221.oasipserver.utils;

import sit.int221.oasipserver.enums.UserRole;
import sit.int221.oasipserver.entities.User;

public class RoleValidate {
    private static final RoleValidate roleValidate = new RoleValidate();

    public RoleValidate() {
    }

    public boolean roleCheck(User newUser) {
        for (UserRole role : UserRole.values()) {
            if(role.equals(newUser.getRole()))
                return false;
        }
        return true;
    }

    public static RoleValidate getInstance() {
        return roleValidate;
    }
}
