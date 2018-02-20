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

  public void setUser(User user) {
    this.user = user;
  }

  public void addCreatedDocument(Document document) {
    createdDocuments.add(document);
  }
}
