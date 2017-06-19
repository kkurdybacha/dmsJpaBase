package pl.com.bottega.dms.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "author")
    private Set<Document> createdDocuments = new HashSet<>();

    @OneToMany
    @JoinColumn(name = "publisher_id")
    private Set<Document> publishedDocuments = new HashSet<>();

    @ManyToMany(mappedBy = "readers")
    private Set<Document> readDocuments = new HashSet<>();

    @OneToOne(mappedBy = "employee")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<Document> getCreatedDocuments() {
        return createdDocuments;
    }

    public Set<Document> getPublishedDocuments() {
        return publishedDocuments;
    }

    public Set<Document> getReadDocuments() {
        return readDocuments;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
