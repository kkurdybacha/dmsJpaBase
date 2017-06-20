package pl.com.bottega.dms.api;

import pl.com.bottega.dms.model.DocumentStatus;

public class DocumentDto {

    private String number;
    private DocumentStatus status;
    private String content;
    private String title;

    public DocumentDto(String number, DocumentStatus status, String content, String title) {
        this.number = number;
        this.status = status;
        this.content = content;
        this.title = title;
    }

    public String getNumber() {
        return number;
    }

    public DocumentStatus getStatus() {
        return status;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        return "DocumentDto{" +
                "number='" + number + '\'' +
                ", status=" + status +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
