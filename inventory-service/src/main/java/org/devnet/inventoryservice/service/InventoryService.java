package org.devnet.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devnet.inventoryservice.payload.InventoryResponse;
import org.devnet.inventoryservice.repository.InventoryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public List<InventoryResponse> isInStock(List<String> skuCodes) throws InterruptedException {
        // simulating a slow behaviour inside the inventory service
        log.info("Wait Started");
        Thread.sleep(10000);
        log.info("Wait Ended");
        return inventoryRepository
                .findBySkuCodeIn(skuCodes)
                .stream()
                .map(inventory -> InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity() > 0)
                            .build())
                .toList();
    }
}
