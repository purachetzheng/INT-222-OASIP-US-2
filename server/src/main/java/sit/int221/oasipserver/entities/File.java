package sit.int221.oasipserver.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "files")
public class File {
    @Id
    @Column(name="fileID", nullable = false)
    private String id;

    @Column(name="fileName", nullable = false, length = 45)
    private String fileName;

    @Column(name="fileSize", nullable = false)
    private Integer fileSize;
}
