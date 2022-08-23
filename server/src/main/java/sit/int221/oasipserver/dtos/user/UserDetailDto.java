package sit.int221.oasipserver.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.oasipserver.enums.Role;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailDto {
    private String name;
    private String email;
    private Role role;
    private Instant createdOn;
    private Instant updatedOn;
    
    public void setEmail(String email) {
        this.email = email.trim();
    }
}
