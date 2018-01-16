package pl.com.bottega.dms.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import static pl.com.bottega.dms.model.DocumentStatus.DRAFT;
import static pl.com.bottega.dms.model.DocumentStatus.VERIFIED;
import static sun.java2d.cmm.kcms.CMM.checkStatus;

@Entity
public class Document {

  @Id
  @GeneratedValue
  private Long id;
  private DocumentStatus documentStatus;

  private Long creatorId;

  private String title, content;
  private Long editorId;

  private Document() {
  }

  public Document(String title, Long creatorId) {
    this.title = title;
    this.creatorId = creatorId;
    this.documentStatus = DRAFT;
  }

  public void edit(String title, String content, Long editorId) {
    checkStatus(DRAFT, VERIFIED);
    this.title = title;
    this.content = content;
    this.editorId = editorId;
  }

  private void checkStatus(DocumentStatus... allowedStated) {
    for(DocumentStatus status : allowedStated)
      if(status == documentStatus)
        return;
    throw new IllegalStateException("Invalid status");
  }

}
