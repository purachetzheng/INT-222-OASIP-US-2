package sit.int221.oasipserver.dtos.eventCategory;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryOwnerDto {
    private Integer id;
    private Integer userID;
    private Integer eventCategoryId;
}
