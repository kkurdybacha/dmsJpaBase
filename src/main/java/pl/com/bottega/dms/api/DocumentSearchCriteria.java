package pl.com.bottega.dms.api;

import pl.com.bottega.dms.model.DocumentStatus;

public class DocumentSearchCriteria {

    private DocumentStatus status;
    private String title;
    private String content;
    private Long authorId;

    public DocumentStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getAuthorId() {
        return authorId;
    }

    public void setStatus(DocumentStatus status) {
        this.status = status;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }
}
