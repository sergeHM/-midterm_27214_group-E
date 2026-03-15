package com.hotel.repository;

import com.hotel.entity.FoodOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FoodOrderRepository extends JpaRepository<FoodOrder, Long> {
    List<FoodOrder> findByBookingId(Long bookingId);

    @Query("SELECT COALESCE(SUM(fo.totalPrice), 0) FROM FoodOrder fo WHERE fo.booking.id = :bookingId")
    Double sumTotalByBookingId(@Param("bookingId") Long bookingId);

    @Query("SELECT COALESCE(SUM(fo.totalPrice), 0) FROM FoodOrder fo WHERE fo.booking.id = :bookingId")
    Double getTotalFoodCostForBooking(@Param("bookingId") Long bookingId);
}
