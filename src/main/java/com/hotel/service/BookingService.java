package com.hotel.service;

import com.hotel.entity.*;
import com.hotel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    
    @Transactional
    public Booking checkIn(Long userId, Long roomId, String checkInStr, String checkOutStr) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found: " + roomId));

        if (!room.getIsAvailable())
            throw new RuntimeException("Room " + room.getRoomNumber() + " is not available.");

        LocalDate checkIn  = LocalDate.parse(checkInStr);
        LocalDate checkOut = LocalDate.parse(checkOutStr);

        if (!checkOut.isAfter(checkIn))
            throw new RuntimeException("Check-out date must be after check-in date.");

        int nights = (int) ChronoUnit.DAYS.between(checkIn, checkOut);
        double roomTotal = nights * room.getPricePerNight();

        
        room.setIsAvailable(false);
        roomRepository.save(room);

        return bookingRepository.save(Booking.builder()
                .user(user).room(room)
                .checkInDate(checkIn).checkOutDate(checkOut)
                .totalNights(nights).roomTotal(roomTotal)
                .status(Booking.BookingStatus.CONFIRMED)
                .build());
    }

    
    public Page<Booking> getAll(int page, int size) {
        return bookingRepository.findAll(PageRequest.of(page, size, Sort.by("bookedAt").descending()));
    }

    
    public Booking getById(Long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found: " + id));
    }

    
    public List<Booking> getByUser(Long userId) {
        return bookingRepository.findByUserId(userId);
    }

    
    public List<Booking> getActive() {
        return bookingRepository.findByStatus(Booking.BookingStatus.CONFIRMED);
    }

    
    @Transactional
    public Booking checkOut(Long bookingId) {
        Booking booking = getById(bookingId);
        if (booking.getStatus() != Booking.BookingStatus.CONFIRMED)
            throw new RuntimeException("Booking is not in CONFIRMED status.");
        booking.setStatus(Booking.BookingStatus.CHECKED_OUT);
        booking.getRoom().setIsAvailable(true);
        roomRepository.save(booking.getRoom());
        return bookingRepository.save(booking);
    }

    
    @Transactional
    public Booking cancel(Long bookingId) {
        Booking booking = getById(bookingId);
        booking.setStatus(Booking.BookingStatus.CANCELLED);
        booking.getRoom().setIsAvailable(true);
        roomRepository.save(booking.getRoom());
        return bookingRepository.save(booking);
    }

    
    @Transactional
    public String delete(Long id) {
        Booking b = getById(id);
        if (b.getStatus() == Booking.BookingStatus.CONFIRMED) {
            b.getRoom().setIsAvailable(true);
            roomRepository.save(b.getRoom());
        }
        bookingRepository.delete(b);
        return "Booking #" + id + " deleted successfully.";
    }
}
