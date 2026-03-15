package com.hotel.service;

import com.hotel.entity.*;
import com.hotel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FoodOrderService {

    private final FoodOrderRepository foodOrderRepository;
    private final BookingRepository   bookingRepository;
    private final FoodItemRepository  foodItemRepository;

    
    @Transactional
    public FoodOrder placeOrder(Long bookingId, Long foodItemId, Integer quantity) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));
        if (booking.getStatus() != Booking.BookingStatus.CONFIRMED)
            throw new RuntimeException("Cannot order food for a non-active booking.");

        FoodItem item = foodItemRepository.findById(foodItemId)
                .orElseThrow(() -> new RuntimeException("Food item not found: " + foodItemId));
        if (!item.getIsAvailable())
            throw new RuntimeException("'" + item.getItemName() + "' is currently unavailable.");

        double unitPrice  = item.getPrice();
        double totalPrice = unitPrice * quantity;

        return foodOrderRepository.save(FoodOrder.builder()
                .booking(booking).foodItem(item)
                .quantity(quantity).unitPrice(unitPrice)
                .totalPrice(totalPrice)
                .status(FoodOrder.OrderStatus.PENDING).build());
    }

    
    public List<FoodOrder> getByBooking(Long bookingId) { return foodOrderRepository.findByBookingId(bookingId); }

    
    public FoodOrder getById(Long id) {
        return foodOrderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food order not found: " + id));
    }

    
    @Transactional
    public FoodOrder updateStatus(Long orderId, String status) {
        FoodOrder order = getById(orderId);
        order.setStatus(FoodOrder.OrderStatus.valueOf(status.toUpperCase()));
        return foodOrderRepository.save(order);
    }

    
    @Transactional
    public String cancel(Long orderId) {
        FoodOrder order = getById(orderId);
        order.setStatus(FoodOrder.OrderStatus.CANCELLED);
        foodOrderRepository.save(order);
        return "Food order #" + orderId + " cancelled successfully.";
    }

    public Double getTotalFoodCost(Long bookingId) {
        return foodOrderRepository.getTotalFoodCostForBooking(bookingId);
    }
}
