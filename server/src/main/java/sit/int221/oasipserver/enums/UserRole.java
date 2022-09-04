package sit.int221.oasipserver.enums;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum UserRole {
    admin,
    lecturer,
    student
}
