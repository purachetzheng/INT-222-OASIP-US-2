package sit.int221.oasipserver.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.Instant;
@Setter
@Getter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userID", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @Column(name = "role", nullable = false, length = 10)
    private String role;


    @CreationTimestamp
    @Column(name = "createdOn", insertable = false)
    private Instant createdOn;


    @UpdateTimestamp
    @Column(name = "updatedOn", insertable = false)
    private Instant updatedOn;
}
