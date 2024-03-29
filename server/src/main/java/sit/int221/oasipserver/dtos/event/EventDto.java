package sit.int221.oasipserver.dtos.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventDto {
    private Integer id;
    private String bookingName;
    private String bookingEmail;
    private Integer eventDuration;
    private Instant eventStartTime;
    private String eventNotes;
    private Integer eventCategoryId;
    private String eventCategoryName;
    private String fileName;
}
