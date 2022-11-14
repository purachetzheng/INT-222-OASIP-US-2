package sit.int221.oasipserver.dtos.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;
import sit.int221.oasipserver.dtos.FileDto;
import sit.int221.oasipserver.dtos.eventCategory.EventcategoryDto;
import sit.int221.oasipserver.entities.Eventcategory;
import sit.int221.oasipserver.entities.File;

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
    private Eventcategory eventCategory;
//    private String fileName;
    private File file;

}
