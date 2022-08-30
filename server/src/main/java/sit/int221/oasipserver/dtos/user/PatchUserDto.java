package sit.int221.oasipserver.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sit.int221.oasipserver.annotations.ValueOfEnum;
import sit.int221.oasipserver.enums.UserRole;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchUserDto {

//    private Integer id;

    @Size(max = 100, message = "size must be between 1 and 100")
    private String name;

    @Size(max = 50, message = "size must be between 1 and 50")
    @Email(message = "must be a well-formed email address")
    private String email;

    @ValueOfEnum(enumClass = UserRole.class)
    private String role;

    private Instant createdOn;

    private Instant updatedOn;
}

