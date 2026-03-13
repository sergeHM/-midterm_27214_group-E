package com.hotel.controller;

import com.hotel.entity.User;
import com.hotel.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    
    
    @PostMapping
    public ResponseEntity<User> create(@RequestParam String fullName,
                                       @RequestParam String email,
                                       @RequestParam(required = false) String phone,
                                       @RequestParam Long locationId) {
        return ResponseEntity.ok(userService.create(fullName, email, phone, locationId));
    }

    
    
    @GetMapping
    public ResponseEntity<Page<User>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fullName") String sortBy) {
        return ResponseEntity.ok(userService.getAll(page, size, sortBy));
    }

    
    
    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getById(id));
    }

    
    
    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id,
                                       @RequestParam(required = false) String fullName,
                                       @RequestParam(required = false) String email,
                                       @RequestParam(required = false) String phone,
                                       @RequestParam(required = false) Long locationId) {
        return ResponseEntity.ok(userService.update(id, fullName, email, phone, locationId));
    }

    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(userService.delete(id));
    }

    
    
    @PutMapping("/{id}/roles")
    public ResponseEntity<User> assignRoles(@PathVariable Long id,
                                            @RequestParam List<String> roles) {
        return ResponseEntity.ok(userService.assignRoles(id, roles));
    }

    
    
    
    @GetMapping("/by-province")
    public ResponseEntity<List<User>> byProvince(@RequestParam String query) {
        return ResponseEntity.ok(userService.findByProvince(query));
    }

    
    
    @GetMapping("/by-province/paged")
    public ResponseEntity<Page<User>> byProvincePaged(@RequestParam String query,
                                                       @RequestParam(defaultValue = "0") int page,
                                                       @RequestParam(defaultValue = "5") int size) {
        return ResponseEntity.ok(userService.findByProvincePaged(query, page, size));
    }
}
