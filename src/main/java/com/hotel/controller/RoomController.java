package com.hotel.controller;

import com.hotel.entity.Room;
import com.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    
    
    @PostMapping
    public ResponseEntity<Room> create(@RequestParam String roomNumber,
                                       @RequestParam String type,
                                       @RequestParam Double price,
                                       @RequestParam(required = false) String features) {
        return ResponseEntity.ok(roomService.create(roomNumber, type, price, features));
    }

    
    
    @GetMapping
    public ResponseEntity<Page<Room>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "roomNumber") String sortBy) {
        return ResponseEntity.ok(roomService.getAll(page, size, sortBy));
    }

    
    
    @GetMapping("/{id}")
    public ResponseEntity<Room> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getById(id));
    }

    
    
    @GetMapping("/available")
    public ResponseEntity<List<Room>> getAvailable() {
        return ResponseEntity.ok(roomService.getAvailable());
    }

    
    
    @GetMapping("/type/{type}")
    public ResponseEntity<List<Room>> getByType(@PathVariable String type) {
        return ResponseEntity.ok(roomService.getByType(type));
    }

    
    
    @PutMapping("/{id}")
    public ResponseEntity<Room> update(@PathVariable Long id,
                                       @RequestParam(required = false) String roomNumber,
                                       @RequestParam(required = false) String type,
                                       @RequestParam(required = false) Double price,
                                       @RequestParam(required = false) String features) {
        return ResponseEntity.ok(roomService.update(id, roomNumber, type, price, features));
    }

    
    
    @PutMapping("/{id}/book")
    public ResponseEntity<Room> book(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.book(id));
    }

    
    
    @PutMapping("/{id}/unbook")
    public ResponseEntity<Room> unbook(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.unbook(id));
    }

    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.delete(id));
    }
}
