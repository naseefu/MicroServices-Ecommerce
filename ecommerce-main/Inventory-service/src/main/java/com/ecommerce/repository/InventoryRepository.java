package com.ecommerce.repository;

import com.ecommerce.entity.Inventory;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends MongoRepository<Inventory,String> {
    Inventory findByProductId(String productId);
}
