package org.devnet.inventoryservice.repository;

import org.devnet.inventoryservice.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Boolean existsBySkuCode(String skuCode);
}
