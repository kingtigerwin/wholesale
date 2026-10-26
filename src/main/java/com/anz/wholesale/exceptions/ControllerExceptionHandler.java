package com.anz.wholesale.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
@Slf4j
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {UsernameNotFoundException.class})
    public ResponseEntity<WholesaleErrorDto> handleUsernameNotFoundException(UsernameNotFoundException e) {
        // Here log level should not be error as this is customer input related error.
        // For Production support experiences, It's not part of system level error and should not be involved in the production support.
        log.info("Username is not found.", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new WholesaleErrorDto("USERNAME_NOT_FOUND", "User name is not found"));
    }

    @ExceptionHandler(value = {InvalidAccountException.class})
    public ResponseEntity<WholesaleErrorDto> handleInvalidAccountException(InvalidAccountException e) {
        log.info("No such an account id", e);
        return new ResponseEntity(new WholesaleErrorDto("INVALID_ACCOUNT_ID", "Invalid account id"), HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(value = {TransactionNotExistException.class})
    public ResponseEntity<WholesaleErrorDto> handleTransactionNotExistException(TransactionNotExistException e) {
        log.info("The account does not have transactions", e);
        return new ResponseEntity(new WholesaleErrorDto("TRANSACTION_NOT_EXIST", e.getMessage()), HttpStatus.OK);
    }

    // @PreAuthorize denials surface here as AuthorizationDeniedException; without this handler
    // they would fall into the catch-all below and be reported as 503.
    @ExceptionHandler(value = {AccessDeniedException.class})
    public ResponseEntity<WholesaleErrorDto> handleAccessDeniedException(AccessDeniedException e) {
        log.info("Access is denied", e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new WholesaleErrorDto("ACCESS_DENIED", "Access is denied"));
    }

    @ExceptionHandler(value = {MethodArgumentTypeMismatchException.class})
    public ResponseEntity<WholesaleErrorDto> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.info("Invalid request parameter", e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new WholesaleErrorDto("INVALID_PARAMETER", String.format("Invalid value for parameter %s", e.getName())));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WholesaleErrorDto> handleException(Exception exception, WebRequest request){
        log.error("There is Exception occurred",exception);
        return new ResponseEntity(new WholesaleErrorDto(exception.getMessage(), "error"), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
