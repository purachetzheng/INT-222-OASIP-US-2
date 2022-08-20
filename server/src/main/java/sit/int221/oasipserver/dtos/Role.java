package sit.int221.oasipserver.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public enum Role {
    admin,
    lecturer,
    student
}
