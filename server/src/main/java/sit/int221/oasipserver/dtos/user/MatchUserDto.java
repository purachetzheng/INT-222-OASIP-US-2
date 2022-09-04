package sit.int221.oasipserver.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MatchUserDto {

//    private Integer id;

    @NotBlank(message = "must not be blank")
    @Size(min = 8, max = 14, message = "size must be between 8 and 14")
    private String password;

    @Size(max = 50, message = "size must be between 1 and 50")
    @Email(message = "must be a well-formed email address")
    @NotBlank(message = "must not be blank")
    private String email;

    public void setEmail(String email) {
        this.email = email.trim();
    }
}
