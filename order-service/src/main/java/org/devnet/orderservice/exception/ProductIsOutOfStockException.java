package org.devnet.orderservice.exception;

public class ProductIsOutOfStockException extends RuntimeException {
    public ProductIsOutOfStockException(String msg) {
        super(msg);
    }
}
