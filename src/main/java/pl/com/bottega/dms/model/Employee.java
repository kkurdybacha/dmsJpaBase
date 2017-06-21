package pl.com.bottega.dms.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Employee extends BaseEntity {

    @OneToMany(mappedBy = "author", cascade = CascadeType.PERSIST)
    private Set<Document> createdDocuments = new HashSet<>();

    @OneToMany(mappedBy = "verifier", cascade = CascadeType.PERSIST)
    private Set<Document> verifiedDocuments = new HashSet<>();

    @OneToMany(mappedBy = "publisher", cascade = CascadeType.PERSIST)
    private Set<Document> publishedDocuments = new HashSet<>();

    @ManyToMany(mappedBy = "readers", cascade = CascadeType.PERSIST)
    private Set<Document> readDocuments = new HashSet<>();

    @OneToOne(mappedBy = "employee", cascade = CascadeType.REMOVE)
    private User user;

    @ElementCollection
    @OrderColumn
    public List<PhoneNumber> phoneNumbers = new ArrayList<>();

    public Set<Document> getCreatedDocuments() {
        return createdDocuments;
    }


    public Set<Document> getVerifiedDocuments() {
        return verifiedDocuments;
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
