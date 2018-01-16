package pl.com.bottega.dms.api;

import java.util.List;

public interface DocumentReader {

    List<DocumentDto> searchDocumentDtos(DocumentSearchCriteria criteria);

    DocumentDto get(Long id);

}
