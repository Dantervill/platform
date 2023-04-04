package org.devnet.orderservice.exceptionhandler;

import org.devnet.orderservice.exception.ProductIsOutOfStockException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductIsOutOfStockException.class)
    public ResponseEntity<?> handle(ProductIsOutOfStockException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
