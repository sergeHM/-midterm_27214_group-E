package com.hotel.controller;

import com.hotel.entity.FoodItem;
import com.hotel.service.FoodItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/food-items")
@RequiredArgsConstructor
public class FoodItemController {

    private final FoodItemService foodItemService;

    
    @PostMapping
    public ResponseEntity<FoodItem> create(@RequestParam String name,
                                           @RequestParam String category,
                                           @RequestParam Double price,
                                           @RequestParam(required = false) String description) {
        return ResponseEntity.ok(foodItemService.create(name, category, price, description));
    }

    
    @GetMapping
    public ResponseEntity<List<FoodItem>> getAll() {
        return ResponseEntity.ok(foodItemService.getAll());
    }

    
    @GetMapping("/available")
    public ResponseEntity<List<FoodItem>> getAvailable() {
        return ResponseEntity.ok(foodItemService.getAvailable());
    }

    
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<FoodItem>> getByCategory(@PathVariable String category) {
        return ResponseEntity.ok(foodItemService.getByCategory(category));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<FoodItem> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foodItemService.getById(id));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> update(@PathVariable Long id,
                                           @RequestParam(required = false) String name,
                                           @RequestParam(required = false) String category,
                                           @RequestParam(required = false) Double price,
                                           @RequestParam(required = false) String description,
                                           @RequestParam(required = false) Boolean available) {
        return ResponseEntity.ok(foodItemService.update(id, name, category, price, description, available));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(foodItemService.delete(id));
    }
}
