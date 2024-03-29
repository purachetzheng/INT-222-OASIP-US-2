package sit.int221.oasipserver.dtos.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostEventDto {
    private Integer id;
    @Size(max = 100, message = "size must be between 1 and 100")
    @NotBlank(message = "must not be blank")
    private String bookingName;

    @Size(max = 100, message = "size must be between 1 and 100")
    @Email(message = "must be a well-formed email address")
    @NotBlank(message = "must not be blank")
    private String bookingEmail;
    //ไว้ set ก่อนการ modelMap
    private Integer eventDuration;
    @NotNull(message = "must not be null")
    @Future(message = "must be a future date")
    private Instant eventStartTime;
    @Size(min = 0, max = 500, message = "size must be between 0 and 500")
    private String eventNotes;
    @NotNull(message = "must not be null")
    private Integer eventCategoryId;
    //ไว้ set ก่อนการ modelMap เพื่อไม่ให้ return กลับแล้ว
    // eventCategoryName null (ปัญหาเมื่อ ใช้การสร้าง id เอง)
    private String eventCategoryName;

    private MultipartFile file;

}
