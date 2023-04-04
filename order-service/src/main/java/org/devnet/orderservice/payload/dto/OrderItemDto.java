package org.devnet.orderservice.payload.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
public class OrderItemDto implements Serializable {
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
