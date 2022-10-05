package sit.int221.oasipserver.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.oasipserver.enums.UserRole;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDtoImpl {
    private String name;
    private String email;
    private UserRole role;
}
