package pl.com.bottega.dms.model;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;

@Entity
public class Employee {

  @Id
  @GeneratedValue
  private Long id;

  @OneToOne(mappedBy = "employee")
  private User user;

  @OneToMany(mappedBy = "author")
  private Collection<Document> createdDocuments = new LinkedList<>();

  @OneToMany(mappedBy = "verifier")
  private Collection<Document> verifiedDocuments = new LinkedList<>();

  @ManyToMany(mappedBy = "readBy")
  private Collection<Document> readDocuments = new LinkedList<>();

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "number", column = @Column(name = "work_number")),
      @AttributeOverride(name = "countryCode", column = @Column(name = "work_country_code")),
  })
  private PhoneNumber workPhone;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name = "number", column = @Column(name = "home_number")),
      @AttributeOverride(name = "countryCode", column = @Column(name = "home_country_code")),
  })
  private PhoneNumber homePhone;

  public void setUser(User user) {
    this.user = user;
  }

  public void addCreatedDocument(Document document) {
    createdDocuments.add(document);
  }
}
