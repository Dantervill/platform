package org.devnet.orderservice.payload.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.devnet.orderservice.payload.dto.OrderItemDto;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderResponse implements Serializable {
    private String orderNumber;
    private List<OrderItemDto> orderItems;
}
