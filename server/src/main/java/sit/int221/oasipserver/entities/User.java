package sit.int221.oasipserver.entities;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import sit.int221.oasipserver.enums.UserRole;

import javax.persistence.*;
import java.time.Instant;
import java.util.*;

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

    @Column(name = "password", nullable = false, length = 100)
    private String password;

    @Column(name = "email", nullable = false, length = 50, unique = true)
    private String email;

//    @ElementCollection
//    @Enumerated(EnumType.STRING)
//    @Column(name = "role", nullable = false, length = 10)
//    private Set<UserRole> role = new HashSet<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, length = 10)
    private UserRole role;

    @CreationTimestamp
    @Column(name = "createdOn", insertable = false)
    private Instant createdOn;

    @UpdateTimestamp
    @Column(name = "updatedOn", insertable = false)
    private Instant updatedOn;
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })

    @JoinTable(name = "eventcategoryowner",
            joinColumns = { @JoinColumn(name = "userId") },
            inverseJoinColumns = { @JoinColumn(name = "eventCategoryId") })
    private Set<Eventcategory> categoriesOwner = new HashSet<>();
    
    public void setEmail(String email) {
        this.email = email.trim();
    }
}
