package com.hotel.controller;

import com.hotel.entity.Booking;
import com.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    
    @PostMapping("/checkin")
    public ResponseEntity<Booking> checkIn(@RequestParam Long userId,
                                           @RequestParam Long roomId,
                                           @RequestParam String checkIn,
                                           @RequestParam String checkOut) {
        return ResponseEntity.ok(bookingService.checkIn(userId, roomId, checkIn, checkOut));
    }

    
    @GetMapping
    public ResponseEntity<Page<Booking>> getAll(
            @RequestParam(defaultValue = "0")  int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(bookingService.getAll(page, size));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.getById(id));
    }

    
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(bookingService.getByUser(userId));
    }

    
    @GetMapping("/active")
    public ResponseEntity<List<Booking>> getActive() {
        return ResponseEntity.ok(bookingService.getActive());
    }

    
    @PutMapping("/{id}/checkout")
    public ResponseEntity<Booking> checkOut(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.checkOut(id));
    }

    
    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancel(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.cancel(id));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(bookingService.delete(id));
    }
}
