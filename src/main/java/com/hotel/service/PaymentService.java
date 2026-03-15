package com.hotel.service;

import com.hotel.entity.*;
import com.hotel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final FoodOrderRepository foodOrderRepository;

    @Transactional
    public Payment checkout(Long bookingId, String method) {
        Booking booking = bookingRepository.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found: " + bookingId));

        if (paymentRepository.existsByBookingId(bookingId)) {
            throw new RuntimeException("Booking " + bookingId + " already has a payment.");
        }

        Double foodTotal = foodOrderRepository.sumTotalByBookingId(bookingId);
        if (foodTotal == null) foodTotal = 0.0;

        double roomAmount = booking.getRoomTotal() != null ? booking.getRoomTotal() : 0.0;
        double total = roomAmount + foodTotal;

        Payment.PaymentMethod paymentMethod;
        try {
            paymentMethod = Payment.PaymentMethod.valueOf(method.toUpperCase());
        } catch (Exception e) {
            throw new RuntimeException("Invalid payment method. Use: CASH, CARD, or MOBILE_MONEY");
        }

        return paymentRepository.save(Payment.builder()
            .booking(booking)
            .roomAmount(roomAmount)
            .foodAmount(foodTotal)
            .totalAmount(total)
            .paymentMethod(paymentMethod)
            .paymentStatus(Payment.PaymentStatus.PAID)
            .transactionRef("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase())
            .build());
    }

    public Payment getByBookingId(Long bookingId) {
        return paymentRepository.findByBookingId(bookingId)
            .orElseThrow(() -> new RuntimeException("No payment found for booking: " + bookingId));
    }

    public Double getTotalRevenue() {
        Double revenue = paymentRepository.getTotalRevenue();
        return revenue != null ? revenue : 0.0;
    }
}
