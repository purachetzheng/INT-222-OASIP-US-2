package sit.int221.oasipserver.dtos.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PatchEventDto {

    @Future(message = "must be a future date")
    private Instant eventStartTime;

    @Size(min = 0, max = 500,
            message = "size must be between 0 and 500")
    private String eventNotes;

    private MultipartFile file;

}
