package com.spring.ai.basic.service;

import com.spring.ai.basic.entity.ShoppingItem;
import com.spring.ai.basic.entity.User;
import com.spring.ai.basic.entity.enums.ShoppingItem.ShoppingItemStatus;
import com.spring.ai.basic.repository.ShoppingItemRepository;
import com.spring.ai.basic.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ShoppingItemServiceTest {

    @Autowired
    private ShoppingItemRepository shoppingItemRepository;

    @Autowired
    private ShoppingItemService shoppingItemService;

    @Autowired
    private UserRepository userRepository;

    private String userId;
    private ShoppingItem item1;
    private ShoppingItem item2;
    private ShoppingItem item3;

    @BeforeEach
    void setUp() {
        // Generate Test User
        User user = User.builder()
                .email("taskService@example.com")
                .name("Test User")
                .build();
        userRepository.save(user);
        userId = userRepository.findAll().get(0).getUserId();

        // Generate Test Shopping Items
        item1 = ShoppingItem.builder()
                .userId(userId)
                .itemName("book")
                .quantity(1)
                .status(ShoppingItemStatus.PENDING)
                .build();

        item2 = ShoppingItem.builder()
                .userId(userId)
                .itemName("pen")
                .quantity(2)
                .status(ShoppingItemStatus.PENDING)
                .build();

        item3 = ShoppingItem.builder()
                .userId(userId)
                .itemName("notebook")
                .quantity(3)
                .status(ShoppingItemStatus.PENDING)
                .build();

        shoppingItemRepository.saveAll(List.of(item1, item2, item3));
    }

    @Test
    void testAddShoppingItem() {
        //given
        String itemName = "eraser";
        int quantity = 1;
        String notes = "small";

        //when
        ShoppingItem newItem = shoppingItemService.addShoppingItem(userId, itemName, quantity, notes);

        //then
        assertNotNull(newItem.getItemId());
        assertEquals("eraser", newItem.getItemName());
        assertEquals(1, newItem.getQuantity());
        assertEquals(ShoppingItemStatus.PENDING, newItem.getStatus());
    }

    @Test
    void testGetShoppingItems() {
        //given
        //userId
        
        //when
        List<ShoppingItem> items = shoppingItemService.getShoppingItems(userId);

        //then
        assertEquals(3, items.size());
    }

    @Test
    void testDeleteShoppingItem() {
        //given
        //userId
        //item1
        
        //when
        ShoppingItem deleted = shoppingItemService.deleteShoppingItem(userId, item1.getItemId());

        //then
        assertEquals(ShoppingItemStatus.CANCELLED, deleted.getStatus());
        ShoppingItem fetched = shoppingItemRepository.findById(item1.getItemId()).orElseThrow();
        assertEquals(ShoppingItemStatus.CANCELLED, fetched.getStatus());
    }

    @Test
    void testSetPurchasedAll() {
        //given
        //userId
        LocalDate purchaseDate = LocalDate.now();

        //when
        List<ShoppingItem> updated = shoppingItemService.setPurchasedAll(userId, purchaseDate);

        //then
        assertEquals(3, updated.size());
        updated.forEach(item -> {
            assertEquals(ShoppingItemStatus.PURCHASED, item.getStatus());
            assertEquals(purchaseDate, item.getPurchasedAt());
        });
    }

    @Test
    void testSetPurchasedSome() {
        LocalDate purchaseDate = LocalDate.now();
        //given
            // Read all pending shopping items
        List<ShoppingItem> allItems = shoppingItemRepository.findByUserUserIdAndStatus(userId, ShoppingItemStatus.PENDING);

            // Shopping items to purchase
        List<Long> idsToPurchase = List.of(allItems.get(0).getItemId(), allItems.get(1).getItemId()); // 2개만

        //when
        List<ShoppingItem> updated = shoppingItemService.setPurchasedSome(userId, idsToPurchase, purchaseDate);

        //then
        assertEquals(2, updated.size());
        updated.forEach(item -> {
            assertTrue(idsToPurchase.contains(item.getItemId()));
            assertEquals(ShoppingItemStatus.PURCHASED, item.getStatus());
            assertEquals(purchaseDate, item.getPurchasedAt());
        });

            //Assert that the remaining items are still pending
        List<ShoppingItem> remaining = shoppingItemRepository.findByUserUserIdAndStatus(userId, ShoppingItemStatus.PENDING);
        assertEquals(1, remaining.size());
    }

    @Test
    void testSetPurchasedExcept() {
        //given 
        LocalDate purchaseDate = LocalDate.now();
            // Read all pending shopping items
        List<ShoppingItem> allItems = shoppingItemRepository.findByUserUserIdAndStatus(userId, ShoppingItemStatus.PENDING);

            // Shopping items not to purchase
        List<Long> excludeIds = List.of(allItems.get(0).getItemId()); // 1개 제외

        //when
        List<ShoppingItem> updated = shoppingItemService.setPurchasedExcept(userId, excludeIds, purchaseDate);

        //then
        assertEquals(2, updated.size());
        updated.forEach(item -> {
            assertFalse(excludeIds.contains(item.getItemId()));
            assertEquals(ShoppingItemStatus.PURCHASED, item.getStatus());
            assertEquals(purchaseDate, item.getPurchasedAt());
        });

            // Assert that excluded items are still pending
        ShoppingItem excludedItem = shoppingItemRepository.findById(excludeIds.get(0)).orElseThrow();
        assertEquals(ShoppingItemStatus.PENDING, excludedItem.getStatus());
    }
}
