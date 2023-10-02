package my.shop.file;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "file")
@Data
public class FileResource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "file_id")
    private Long id;

    private String serverFileName;
    private String viewFileName;

    @Enumerated(EnumType.STRING)
    private FileEnum fileEnum;

}
