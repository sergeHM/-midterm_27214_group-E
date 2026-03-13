package com.hotel.repository;

import com.hotel.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {
    boolean existsByItemName(String itemName);
    List<FoodItem> findByIsAvailable(Boolean available);
    List<FoodItem> findByCategory(FoodItem.FoodCategory category);
}
