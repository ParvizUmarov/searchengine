package searchengine.entity;

import lombok.Data;
import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "ENUM('INDEXING', 'INDEXED', ''FAILED'')", nullable = false)
    private String status;

    @Column(nullable = false)
    private Date date;

    @Column(columnDefinition = "TEXT", name = "last_error")
    private String lastError;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String url;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String name;

}
