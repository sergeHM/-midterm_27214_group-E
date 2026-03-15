package com.hotel.controller;

import com.hotel.entity.UserProfile;
import com.hotel.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService profileService;

    
    
    @PostMapping
    public ResponseEntity<UserProfile> create(
            @RequestParam Long userId,
            @RequestParam String nationalId,
            @RequestParam(required = false) String dob,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String pictureUrl) {
        return ResponseEntity.ok(profileService.create(userId, nationalId, dob, gender, pictureUrl));
    }

    
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserProfile> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.getByUserId(userId));
    }

    
    
    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getById(@PathVariable Long id) {
        return ResponseEntity.ok(profileService.getById(id));
    }

    
    
    @PutMapping("/user/{userId}")
    public ResponseEntity<UserProfile> update(
            @PathVariable Long userId,
            @RequestParam(required = false) String nationalId,
            @RequestParam(required = false) String dob,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String pictureUrl) {
        return ResponseEntity.ok(profileService.update(userId, nationalId, dob, gender, pictureUrl));
    }

    
    
    @DeleteMapping("/user/{userId}")
    public ResponseEntity<String> delete(@PathVariable Long userId) {
        return ResponseEntity.ok(profileService.delete(userId));
    }
}
