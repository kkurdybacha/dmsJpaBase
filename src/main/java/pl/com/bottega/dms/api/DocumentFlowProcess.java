package pl.com.bottega.dms.api;

public interface DocumentFlowProcess {

  Long create(String title, Long creatorId);

  void edit(Long documentId, String title, String content, Long editorId);

  void verify(Long documentId, Long verifierId);

  void publish(Long documentId, Long publisherId, Long departmentId);

  void archive(Long documentId, Long archiverId);

}
