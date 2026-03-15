package com.hotel.repository;

import com.hotel.entity.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    boolean existsByRoomNumber(String roomNumber);
    List<Room> findByIsAvailable(Boolean isAvailable);
    List<Room> findByRoomType(Room.RoomType roomType);
    Page<Room> findAll(Pageable pageable);
}
