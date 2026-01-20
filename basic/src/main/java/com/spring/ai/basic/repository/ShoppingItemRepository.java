package com.spring.ai.basic.repository;

import com.spring.ai.basic.entity.ShoppingItem;
import com.spring.ai.basic.entity.enums.ShoppingItem.ShoppingItemStatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
    
    List<ShoppingItem> findByUserUserIdAndStatus(String userId, ShoppingItemStatus status);
    
}
