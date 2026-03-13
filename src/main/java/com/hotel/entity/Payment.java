package com.hotel.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id", unique = true)
    private Booking booking;

    private Double roomAmount;
    private Double foodAmount;
    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PAID;

    @Column(unique = true)
    private String transactionRef;

    @Builder.Default
    private LocalDateTime paidAt = LocalDateTime.now();

    public enum PaymentMethod {
        CASH, CARD, MOBILE_MONEY
    }

    public enum PaymentStatus {
        PAID, PENDING, FAILED
    }
}
