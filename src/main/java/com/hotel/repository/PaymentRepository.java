package com.hotel.repository;

import com.hotel.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByBookingId(Long bookingId);
    boolean existsByBookingId(Long bookingId);

    @Query("SELECT COALESCE(SUM(p.totalAmount), 0) FROM Payment p WHERE p.paymentStatus = 'PAID'")
    Double getTotalRevenue();
}
