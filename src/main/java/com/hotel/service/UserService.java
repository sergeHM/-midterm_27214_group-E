package com.hotel.service;

import com.hotel.entity.*;
import com.hotel.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final RoleRepository roleRepository;

    
    @Transactional
    public User create(String fullName, String email, String phone, Long locationId) {
        return create(fullName, email, phone, null, locationId);
    }

    @Transactional
    public User create(String fullName, String email, String phone, String nationality, Long locationId) {
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email '" + email + "' is already registered.");
        }
        Location location = locationRepository.findById(locationId)
            .orElseThrow(() -> new RuntimeException("Location not found: " + locationId));
        return userRepository.save(
            User.builder()
                .fullName(fullName)
                .email(email)
                .phoneNumber(phone)
                .nationality(nationality)
                .location(location)
                .build()
        );
    }

    
    public Page<User> getAll(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
        return userRepository.findAll(pageable);
    }

    
    public User getById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    
    @Transactional
    public User update(Long id, String fullName, String email, String phone, Long locationId) {
        User user = getById(id);
        if (fullName != null && !fullName.isBlank()) user.setFullName(fullName);
        if (email != null && !email.isBlank()) {
            
            if (!email.equals(user.getEmail()) && userRepository.existsByEmail(email)) {
                throw new RuntimeException("Email '" + email + "' is already taken.");
            }
            user.setEmail(email);
        }
        if (phone != null && !phone.isBlank()) user.setPhoneNumber(phone);
        if (locationId != null) {
            Location location = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("Location not found: " + locationId));
            user.setLocation(location);
        }
        return userRepository.save(user);
    }

    
    @Transactional
    public String delete(Long id) {
        User user = getById(id);
        userRepository.delete(user);
        return "User '" + user.getFullName() + "' deleted successfully.";
    }

    
    @Transactional
    public User assignRoles(Long userId, List<String> roleNames) {
        User user = getById(userId);
        Set<Role> roles = roleNames.stream().map(name -> {
            if (!roleRepository.existsByRoleName(name)) {
                return roleRepository.save(Role.builder().roleName(name).build());
            }
            return roleRepository.findByRoleName(name).orElseThrow();
        }).collect(Collectors.toSet());
        user.setRoles(roles);
        return userRepository.save(user);
    }

    
    public List<User> findByProvince(String query) {
        return userRepository.findByProvinceCodeOrName(query, query);
    }

    public Page<User> findByProvincePaged(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fullName").ascending());
        return userRepository.findByProvinceCodeOrName(query, query, pageable);
    }
}
