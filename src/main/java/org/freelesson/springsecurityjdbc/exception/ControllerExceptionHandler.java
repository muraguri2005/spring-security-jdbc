package org.freelesson.springsecurityjdbc.exception;


import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.RollbackException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@SuppressWarnings({"all", "unchecked", "rawtypes"})
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

   

    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<?> handleAccessDeniedException(AccessDeniedException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(403, ex.getMessage()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(value = {ResourceAccessException.class})
    public ResponseEntity<?> handleResourceAccessException(ResourceAccessException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(403, ex.getMessage()),
                HttpStatus.FORBIDDEN);
    }



    
    @ExceptionHandler(value = {DataAccessException.class})
    public ResponseEntity<?> handleDataAccessException(DataAccessException ex) {
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(500, ex.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {TransactionSystemException.class})
    public ResponseEntity<?> handleTransactionSystemException(TransactionSystemException ex) {

        Throwable cause = ex.getCause();
        if (cause instanceof RollbackException) {
            Throwable throwable = ex.getCause();
            if (throwable.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException constraintViolationException = (ConstraintViolationException) throwable
                        .getCause();
                HashMap<String, String> errors = new HashMap<>();
                for (ConstraintViolation<?> constraintViolation : constraintViolationException
                        .getConstraintViolations()) {
                    errors.put(constraintViolation.getPropertyPath().toString(),
                            constraintViolation.getMessageTemplate());
                }
                ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Data integrity violation");
                return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(500, "Internal Server Error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

   
    @ExceptionHandler(value = {BadRequestException.class})
    public ResponseEntity<?> handleDatatabBadRequestException(BadRequestException ex) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(ex.getExceptionStatus().value(), ex.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ObjectNotFoundException.class})
    public ResponseEntity<?> handleDatatabObjectNotFoundException(ObjectNotFoundException ex) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(ex.getExceptionStatus().value(), ex.getMessage()), HttpStatus.NOT_FOUND);
    }

   

    @ExceptionHandler(value = {ConstraintViolationException.class})
    protected ResponseEntity<?> handleConflict(ConstraintViolationException oe, WebRequest request) {
    	String message = "";
    	if (oe.getCause()!=null)
    		message = oe.getCause().getMessage();
    	else 
    		message = oe.getMessage();
        return new ResponseEntity<ErrorResponse>(new ErrorResponse(400, message),HttpStatus.BAD_REQUEST);

    }

}
