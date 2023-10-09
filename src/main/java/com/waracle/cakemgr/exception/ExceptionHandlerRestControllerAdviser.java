package com.waracle.cakemgr.exception;

import com.waracle.cakemgr.dto.ErrorDTO;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ExceptionHandlerRestControllerAdviser {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        log.error("Bad Cake Id passed: ", ex);
        return getNewErrorResponseWith(HttpStatus.NOT_FOUND, "No cake found with that id");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format(
                "The provided value '%s' is the incorrect type for the '%s' parameter.", ex.getValue(), ex.getName());
        log.warn(errorMessage);
        return getNewErrorResponseWith(HttpStatus.BAD_REQUEST, errorMessage);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        log.error("Database related error: ", ex.getCause());

        return getNewErrorResponseWith(HttpStatus.INTERNAL_SERVER_ERROR, "We have encountered an error. Please try again later.");
    }


    @ExceptionHandler(CakeManagerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> handleCakeManagerException(CakeManagerException ex) {
        log.error("Internal error encountered: ", ex.getMessage());
        return getNewErrorResponseWith(HttpStatus.INTERNAL_SERVER_ERROR, String.valueOf(ex.getMessage()));
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> adviceServiceErrors(RuntimeException ex) {
        log.error("Service is failed due to in internal error:", ex);
        return getNewErrorResponseWith(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }



    private static ResponseEntity<ErrorDTO> getNewErrorResponseWith(HttpStatus status, String errorMessage){
        return new ResponseEntity<>(ErrorDTO.builder().code(status.toString()).message(errorMessage).build(), status);
    }
}
