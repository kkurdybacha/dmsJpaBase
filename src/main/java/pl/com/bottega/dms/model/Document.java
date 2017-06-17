package pl.com.bottega.dms.model;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime verifiedAt;
    private LocalDateTime publishedAt;

}
