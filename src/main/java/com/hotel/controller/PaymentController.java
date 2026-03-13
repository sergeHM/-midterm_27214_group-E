package com.hotel.controller;

import com.hotel.entity.Payment;
import com.hotel.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    
    @PostMapping("/checkout")
    public ResponseEntity<Payment> checkout(
            @RequestParam Long bookingId,
            @RequestParam String method) {
        return ResponseEntity.ok(paymentService.checkout(bookingId, method));
    }

    
    @GetMapping("/booking/{bookingId}")
    public ResponseEntity<Payment> getByBooking(@PathVariable Long bookingId) {
        return ResponseEntity.ok(paymentService.getByBookingId(bookingId));
    }

    
    @GetMapping("/revenue")
    public ResponseEntity<Double> getRevenue() {
        return ResponseEntity.ok(paymentService.getTotalRevenue());
    }
}
