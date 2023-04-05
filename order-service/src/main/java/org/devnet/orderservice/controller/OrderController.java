package org.devnet.orderservice.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.devnet.orderservice.payload.request.OrderRequest;
import org.devnet.orderservice.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @CircuitBreaker(name = "inventory", fallbackMethod = "fallbackMethod")
    @TimeLimiter(name = "inventory")
    @Retry(name = "inventory")
    public CompletableFuture<ResponseEntity<?>> createOrder(@RequestBody OrderRequest orderRequest) {
        return CompletableFuture.supplyAsync(() -> orderService.createOrder(orderRequest));
    }

    public CompletableFuture<ResponseEntity<?>> fallbackMethod(OrderRequest orderRequest, RuntimeException e) {
        return CompletableFuture
                .supplyAsync(() ->
                        new ResponseEntity<>("Something went wrong! Please order after some time", HttpStatus.BAD_REQUEST));
    }
}
