package com.anz.wholesale.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

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

    @ExceptionHandler(Exception.class)
    public ResponseEntity<WholesaleErrorDto> handleException(Exception exception, WebRequest request){
        log.error("There is Exception occurred",exception);
        return new ResponseEntity(new WholesaleErrorDto(exception.getMessage(), "error"), HttpStatus.SERVICE_UNAVAILABLE);
    }
}
