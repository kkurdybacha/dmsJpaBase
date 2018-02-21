package pl.com.bottega.dms.api;

public interface DocumentFlowProcess {

  Long createDocument();

  void verifyDocument(Long documentId);

}
