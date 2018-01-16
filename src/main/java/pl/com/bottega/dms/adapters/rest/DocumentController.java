package pl.com.bottega.dms.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.dms.api.DocumentFlowProcess;

@RestController
@RequestMapping("/documents")
public class DocumentController {

  @Autowired
  private DocumentFlowProcess documentFlowProcess;

  @PostMapping
  public CreateDocumentResponse create(@RequestBody CreateDocumentRequest request) {
    Long docId = documentFlowProcess.create(request.getTitle(), request.getCreatorId());
    return new CreateDocumentResponse(docId);
  }

}
