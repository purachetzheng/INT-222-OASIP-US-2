package sit.int221.oasipserver.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.enums.UserRole;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Integer id;
    private String name;
    private String email;
    private UserRole role;
    private Instant createdOn;
    private Instant updatedOn;
//    private Set<Eventcategory> categoriesOwner;
    
    public void setEmail(String email) {
        this.email = email.trim();
    }
}
