package com.hotel.repository;

import com.hotel.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByUserId(Long userId);
    List<Booking> findByRoomId(Long roomId);
    List<Booking> findByStatus(Booking.BookingStatus status);
    boolean existsByRoomIdAndStatus(Long roomId, Booking.BookingStatus status);
    Page<Booking> findAll(Pageable pageable);

    @Query("SELECT COALESCE(SUM(b.roomTotal), 0) FROM Booking b WHERE b.status = 'CHECKED_OUT'")
    Double getTotalRoomRevenue();

    @Query("SELECT b FROM Booking b WHERE b.user.id = :userId ORDER BY b.bookedAt DESC")
    List<Booking> findBookingHistoryByUser(@Param("userId") Long userId);
}
