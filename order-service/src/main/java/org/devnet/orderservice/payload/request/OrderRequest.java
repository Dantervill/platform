package org.devnet.orderservice.payload.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.devnet.orderservice.payload.dto.OrderItemDto;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class OrderRequest implements Serializable {
    private List<OrderItemDto> orderItemsDto;
}
