package org.devnet.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devnet.orderservice.model.Order;
import org.devnet.orderservice.payload.request.OrderRequest;
import org.devnet.orderservice.payload.response.OrderResponse;
import org.devnet.orderservice.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final ModelMapper mapper;
    private final OrderRepository orderRepository;

    public ResponseEntity<?> createOrder(OrderRequest orderRequest) {
        Order order = mapper.map(orderRequest, Order.class);
        order.setOrderNumber(UUID.randomUUID().toString());
        order = orderRepository.save(order);
        OrderResponse orderResponse = mapper.map(order, OrderResponse.class);
        return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
    }

}
