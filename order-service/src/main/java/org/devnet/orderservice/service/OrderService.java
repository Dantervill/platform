package org.devnet.orderservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devnet.orderservice.exception.ProductIsOutOfStockException;
import org.devnet.orderservice.model.Order;
import org.devnet.orderservice.model.OrderItem;
import org.devnet.orderservice.payload.request.OrderRequest;
import org.devnet.orderservice.payload.response.InventoryResponse;
import org.devnet.orderservice.payload.response.OrderResponse;
import org.devnet.orderservice.repository.OrderRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private static final String ENDPOINT = "http://localhost:8083/api/v1/inventories";

    private final ModelMapper mapper;
    private final WebClient webClient;
    private final OrderRepository orderRepository;

    public ResponseEntity<?> createOrder(OrderRequest orderRequest) {
        Order order = mapper.map(orderRequest, Order.class);
        order.setOrderNumber(UUID.randomUUID().toString());

        List<String> skuCodes = order.getOrderItems().stream().map(OrderItem::getSkuCode).toList();

        // call inventory service, and place order if product is in stock
        InventoryResponse[] inventoryResponses = getInventoryResponses(skuCodes);

        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);

        if (allProductsInStock) {
            order = orderRepository.save(order);
            OrderResponse orderResponse = mapper.map(order, OrderResponse.class);
            return new ResponseEntity<>(orderResponse, HttpStatus.CREATED);
        }
        String msg = "Some products are out of stock";
        throw new ProductIsOutOfStockException(msg);
    }

    private InventoryResponse[] getInventoryResponses(List<String> skuCodes) {
        return webClient
                .get()
                .uri(ENDPOINT, uriBuilder ->
                        uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
    }

}
