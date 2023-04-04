package org.devnet.inventoryservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.devnet.inventoryservice.repository.InventoryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    public ResponseEntity<?> isInStock(String skuCode) {
        boolean isInStock = inventoryRepository.existsBySkuCode(skuCode);
        return new ResponseEntity<>(isInStock, HttpStatus.OK);
    }
}
