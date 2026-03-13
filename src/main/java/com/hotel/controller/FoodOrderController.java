package com.hotel.controller;

import com.hotel.entity.FoodOrder;
import com.hotel.service.FoodOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/food-orders")
@RequiredArgsConstructor
public class FoodOrderController {

    private final FoodOrderService foodOrderService;

    
    @PostMapping
    public ResponseEntity<FoodOrder> placeOrder(@RequestParam Long bookingId,
                                                @RequestParam Long foodItemId,
                                                @RequestParam(defaultValue = "1") Integer quantity) {
        return ResponseEntity.ok(foodOrderService.placeOrder(bookingId, foodItemId, quantity));
    }

    
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<List<FoodOrder>> getByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(foodOrderService.getByBooking(bookingId));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<FoodOrder> getById(@PathVariable Long id) {
        return ResponseEntity.ok(foodOrderService.getById(id));
    }

    
    
    @PutMapping("/{id}/status")
    public ResponseEntity<FoodOrder> updateStatus(@PathVariable Long id,
                                                   @RequestParam String status) {
        return ResponseEntity.ok(foodOrderService.updateStatus(id, status));
    }

    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(foodOrderService.cancel(id));
    }

    
    @GetMapping("/booking/{bookingId}/total")
    public ResponseEntity<Double> getTotal(@PathVariable Long bookingId) {
        return ResponseEntity.ok(foodOrderService.getTotalFoodCost(bookingId));
    }
}
