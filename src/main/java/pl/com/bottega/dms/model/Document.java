package pl.com.bottega.dms.model;

import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Document extends BaseEntity {

    @NaturalId
    private String number;

    private String title;
    private String content;
    private LocalDateTime verifiedAt;
    private LocalDateTime publishedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee verifier;

    @ManyToOne(fetch = FetchType.LAZY)
    private Employee publisher;

    @Enumerated(EnumType.STRING)
    private DocumentStatus status;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "reader_id")
    )
    private Set<Employee> readers = new HashSet<>();

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public void setVerifiedAt(LocalDateTime verifiedAt) {
        this.verifiedAt = verifiedAt;
    }

    public LocalDateTime getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(LocalDateTime publishedAt) {
        this.publishedAt = publishedAt;
    }

    public Employee getAuthor() {
        return author;
    }

    public void setAuthor(Employee author) {
        this.author = author;
    }

    public Employee getVerifier() {
        return verifier;
    }

    public void setVerifier(Employee verifier) {
        this.verifier = verifier;
    }

    public Employee getPublisher() {
        return publisher;
    }

    public void setPublisher(Employee publisher) {
        this.publisher = publisher;
    }

    public Set<Employee> getReaders() {
        return readers;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }
}
