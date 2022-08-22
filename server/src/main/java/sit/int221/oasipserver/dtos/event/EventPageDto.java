package sit.int221.oasipserver.dtos.event;

import lombok.Getter;
import lombok.Setter;
import sit.int221.oasipserver.dtos.event.EventDto;

import java.util.List;

@Getter
@Setter
public class EventPageDto {
    private List<EventDto> content;
    private int number;
    private int size;
    private int totalPages;
    private int numberOfElements;
    private int totalElements;
    private boolean last;
    private boolean first;
}
