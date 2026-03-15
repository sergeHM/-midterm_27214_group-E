package com.hotel.service;

import com.hotel.entity.User;
import com.hotel.entity.UserProfile;
import com.hotel.repository.UserProfileRepository;
import com.hotel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository profileRepository;
    private final UserRepository userRepository;

    
    @Transactional
    public UserProfile create(Long userId, String nationalId, String dob, String gender, String pictureUrl) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found: " + userId));
        if (profileRepository.findByUserId(userId).isPresent()) {
            throw new RuntimeException("User " + userId + " already has a profile. Use UPDATE instead.");
        }
        if (profileRepository.existsByNationalId(nationalId)) {
            throw new RuntimeException("National ID '" + nationalId + "' already registered.");
        }
        return profileRepository.save(
            UserProfile.builder()
                .nationalId(nationalId)
                .dateOfBirth(dob)
                .gender(gender)
                .profilePictureUrl(pictureUrl)
                .user(user)
                .build()
        );
    }

    
    public UserProfile getByUserId(Long userId) {
        return profileRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("No profile found for user: " + userId));
    }

    
    public UserProfile getById(Long id) {
        return profileRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Profile not found: " + id));
    }

    
    @Transactional
    public UserProfile update(Long userId, String nationalId, String dob, String gender, String pictureUrl) {
        UserProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("No profile for user: " + userId + ". Create one first."));
        if (nationalId != null && !nationalId.isBlank()) profile.setNationalId(nationalId);
        if (dob != null && !dob.isBlank()) profile.setDateOfBirth(dob);
        if (gender != null && !gender.isBlank()) profile.setGender(gender);
        if (pictureUrl != null && !pictureUrl.isBlank()) profile.setProfilePictureUrl(pictureUrl);
        return profileRepository.save(profile);
    }

    
    @Transactional
    public String delete(Long userId) {
        UserProfile profile = profileRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("No profile for user: " + userId));
        profileRepository.delete(profile);
        return "Profile for user " + userId + " deleted successfully.";
    }
}
