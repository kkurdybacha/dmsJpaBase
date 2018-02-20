package pl.com.bottega.dms.model;

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

  public Long getId() {
    return id;
  }

  public void setAuthor(Employee author) {
    this.author = author;
  }
}
