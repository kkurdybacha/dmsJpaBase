package pl.com.bottega.dms.api;

public interface DocumentConfirmation {

  void confirm(Long documentId, Long readerId);

  void confirmFor(Long documentId,
                  Long readerId,
                  Long supervisorId);

}
