package sit.int221.oasipserver.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="eventcategoryowner")
public class EventcategoryOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "userID", nullable = false)
    private Integer userID;

    @Column(name = "eventCategoryId", nullable = false)
    private Integer eventCategoryId;

}


