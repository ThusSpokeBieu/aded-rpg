package github.gmess.aded.web.api.config.advice;

import github.gmess.aded.domain.exceptions.DomainException;
import github.gmess.aded.domain.exceptions.Error;
import github.gmess.aded.domain.exceptions.ForbiddenException;
import github.gmess.aded.domain.exceptions.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(value = ForbiddenException.class)
  public ResponseEntity<?> handleForbiddenException(final ForbiddenException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ApiError.from(ex));
  }

  @ExceptionHandler(value = NotFoundException.class)
  public ResponseEntity<?> handleNotFoundException(final NotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(ex));
  }

  @ExceptionHandler(value = DomainException.class)
  public ResponseEntity<?> handleDomainException(final DomainException ex) {
    return ResponseEntity.unprocessableEntity().body(ApiError.from(ex));
  }

  record ApiError(String message, List<Error> errors) {
    static ApiError from(final DomainException ex) {
      return new ApiError(ex.getMessage(), ex.getErrors());
    }
  }
}
