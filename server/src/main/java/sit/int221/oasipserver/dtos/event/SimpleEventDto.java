package sit.int221.oasipserver.dtos.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.Instant;
import java.util.TimeZone;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleEventDto {
    private Integer id;
    private String bookingName;
    private Integer eventDuration;
    private Instant eventStartTime;
    private Integer eventCategoryId;
    private String eventCategoryName;
}
