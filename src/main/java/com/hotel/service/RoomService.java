package com.hotel.service;

import com.hotel.entity.Room;
import com.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository roomRepository;

    
    @Transactional
    public Room create(String roomNumber, String type, Double price, String features) {
        return create(roomNumber, type, price, null, null, features);
    }

    @Transactional
    public Room create(String roomNumber, String type, Double price, Integer floor, Integer maxOccupancy, String features) {
        if (roomRepository.existsByRoomNumber(roomNumber)) {
            throw new RuntimeException("Room number '" + roomNumber + "' already exists.");
        }
        return roomRepository.save(
            Room.builder()
                .roomNumber(roomNumber)
                .roomType(Room.RoomType.valueOf(type.toUpperCase()))
                .pricePerNight(price)
                .floorNumber(floor)
                .maxOccupancy(maxOccupancy)
                .features(features)
                .isAvailable(true)
                .build()
        );
    }

    
    public Page<Room> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return roomRepository.findAll(pageable);
    }

    
    public Room getById(Long id) {
        return roomRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Room not found: " + id));
    }

    
    public List<Room> getAvailable() {
        return roomRepository.findByIsAvailable(true);
    }

    
    public List<Room> getByType(String type) {
        return roomRepository.findByRoomType(Room.RoomType.valueOf(type.toUpperCase()));
    }

    
    @Transactional
    public Room update(Long id, String roomNumber, String type, Double price, String features) {
        Room room = getById(id);
        if (roomNumber != null && !roomNumber.isBlank()) {
            if (!roomNumber.equals(room.getRoomNumber()) && roomRepository.existsByRoomNumber(roomNumber)) {
                throw new RuntimeException("Room number '" + roomNumber + "' already in use.");
            }
            room.setRoomNumber(roomNumber);
        }
        if (type != null && !type.isBlank()) room.setRoomType(Room.RoomType.valueOf(type.toUpperCase()));
        if (price != null) room.setPricePerNight(price);
        if (features != null && !features.isBlank()) room.setFeatures(features);
        return roomRepository.save(room);
    }

    
    @Transactional
    public Room book(Long id) {
        Room room = getById(id);
        if (!room.getIsAvailable()) {
            throw new RuntimeException("Room " + room.getRoomNumber() + " is already booked.");
        }
        room.setIsAvailable(false);
        return roomRepository.save(room);
    }

    
    @Transactional
    public Room unbook(Long id) {
        Room room = getById(id);
        room.setIsAvailable(true);
        return roomRepository.save(room);
    }

    
    @Transactional
    public String delete(Long id) {
        Room room = getById(id);
        roomRepository.delete(room);
        return "Room " + room.getRoomNumber() + " deleted successfully.";
    }
}
