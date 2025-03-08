package searchengine.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Index;

@Data
@Entity
@Table(name = "page", indexes = @Index(columnList = "path", name = "p_index"))
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "site_id", nullable = false)
    private Site site;

    @Column(nullable = false)
    private String path;

    @Column(nullable = false)
    private Integer code;

    @Column(nullable = false)
    private String content;

}
