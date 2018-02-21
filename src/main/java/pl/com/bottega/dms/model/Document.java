package pl.com.bottega.dms.model;

import pl.com.bottega.dms.infrastructure.DocumentStatusConverter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.LinkedList;

@Entity
public class Document {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(length = 10)
  private String number;

  private String title;

  private LocalDateTime createdAt;

  private LocalDateTime verifiedAt;

  private LocalDateTime publishedAt;

  @Convert(converter = DocumentStatusConverter.class)
  private DocumentStatus status = DocumentStatus.DRAFT;

  @Enumerated(EnumType.STRING)
  private DocumentStatus statusAsEnum = DocumentStatus.DRAFT;

  @ManyToOne
  private Employee author;

  @ManyToOne
  private Employee verifier;

  @ManyToMany
  @JoinTable(
      name="readers",
      joinColumns = @JoinColumn(name = "document_id"),
      inverseJoinColumns = @JoinColumn(name = "employee_id")
  )
  private Collection<Employee> readBy = new LinkedList<>();

  @Column(length = 6 * 1024)
  @Basic(fetch = FetchType.LAZY)
  private String content;

  @Version
  public Long version;

  public Long getId() {
    return id;
  }

  public void setAuthor(Employee author) {
    this.author = author;
  }

  public void verify() {
    if(status != DocumentStatus.DRAFT)
      throw new IllegalStateException();
    status = DocumentStatus.VERIFIED;
  }
}
