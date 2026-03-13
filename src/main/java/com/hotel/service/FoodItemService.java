package com.hotel.service;

import com.hotel.entity.FoodItem;
import com.hotel.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodItemService {

    private final FoodItemRepository foodItemRepository;

    
    @Transactional
    public FoodItem create(String name, String category, Double price, String description) {
        if (foodItemRepository.existsByItemName(name))
            throw new RuntimeException("Food item '" + name + "' already exists.");
        return foodItemRepository.save(FoodItem.builder()
                .itemName(name)
                .category(FoodItem.FoodCategory.valueOf(category.toUpperCase()))
                .price(price).description(description)
                .isAvailable(true).build());
    }

    
    public List<FoodItem> getAll()                      { return foodItemRepository.findAll(); }
    public List<FoodItem> getAvailable()                { return foodItemRepository.findByIsAvailable(true); }
    public List<FoodItem> getByCategory(String cat)    { return foodItemRepository.findByCategory(FoodItem.FoodCategory.valueOf(cat.toUpperCase())); }

    
    public FoodItem getById(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found: " + id));
    }

    
    @Transactional
    public FoodItem update(Long id, String name, String category, Double price, String description, Boolean available) {
        FoodItem item = getById(id);
        if (name        != null && !name.isBlank())        item.setItemName(name);
        if (category    != null && !category.isBlank())    item.setCategory(FoodItem.FoodCategory.valueOf(category.toUpperCase()));
        if (price       != null)                           item.setPrice(price);
        if (description != null && !description.isBlank()) item.setDescription(description);
        if (available   != null)                           item.setIsAvailable(available);
        return foodItemRepository.save(item);
    }

    
    @Transactional
    public String delete(Long id) {
        FoodItem item = getById(id);
        foodItemRepository.delete(item);
        return "Food item '" + item.getItemName() + "' deleted successfully.";
    }
}
