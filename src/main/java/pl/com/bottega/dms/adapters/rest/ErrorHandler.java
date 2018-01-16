package pl.com.bottega.dms.adapters.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.com.bottega.dms.model.DocumentNotFoundException;

@ControllerAdvice
public class ErrorHandler {

  @ExceptionHandler(DocumentNotFoundException.class)
  @ResponseStatus(value= HttpStatus.NOT_FOUND, reason="No such document")
  public void handleDocumentNotFound() {

  }

}
