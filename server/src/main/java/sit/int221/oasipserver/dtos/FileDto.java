package sit.int221.oasipserver.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FileDto {
    private String id;
    private String name;
    private long size;

    public void setSize(MultipartFile file) {
        this.size = file.getSize();
    }
    public void setId(String uuid) {
        this.id = uuid;
    }
    public void setName(MultipartFile file) {
        this.name = file.getOriginalFilename();
    }
}
