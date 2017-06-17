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

    @OneToMany(mappedBy = "verifier")
    private Set<Document> verifiedDocuments = new HashSet<>();

    @OneToMany(mappedBy = "publisher")
    private Set<Document> publishedDocuments = new HashSet<>();

    @ManyToMany(mappedBy = "readers")
    private Set<Document> readDocuments = new HashSet<>();

    @OneToOne(mappedBy = "employee")
    private User user;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "countryCode", column = @Column(name = "work_phone_country_code")),
            @AttributeOverride(name = "number", column = @Column(name = "work_phone_number"))
    })
    private PhoneNumber workPhone;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "countryCode", column = @Column(name = "private_phone_country_code")),
            @AttributeOverride(name = "number", column = @Column(name = "private_phone_number"))
    })
    private PhoneNumber privatePhone;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public PhoneNumber getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(PhoneNumber workPhone) {
        this.workPhone = workPhone;
    }

    public PhoneNumber getPrivatePhone() {
        return privatePhone;
    }

    public void setPrivatePhone(PhoneNumber privatePhone) {
        this.privatePhone = privatePhone;
    }
}
