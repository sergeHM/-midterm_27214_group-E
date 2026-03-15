package com.hotel.controller;

import com.hotel.entity.Location;
import com.hotel.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;

    
    @PostMapping
    public ResponseEntity<Location> create(
            @RequestParam Long provinceId,
            @RequestParam String cityName,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String village) {
        return ResponseEntity.ok(locationService.save(provinceId, cityName, district, sector, village));
    }

    
    @GetMapping
    public ResponseEntity<Page<Location>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "cityName") String sortBy) {
        return ResponseEntity.ok(locationService.getAll(page, size, sortBy));
    }

    
    @GetMapping("/{id}")
    public ResponseEntity<Location> getById(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.getById(id));
    }

    
    @GetMapping("/by-province/{provinceId}")
    public ResponseEntity<List<Location>> getByProvince(@PathVariable Long provinceId) {
        return ResponseEntity.ok(locationService.getByProvince(provinceId));
    }

    
    @PutMapping("/{id}")
    public ResponseEntity<Location> update(
            @PathVariable Long id,
            @RequestParam(required = false) String cityName,
            @RequestParam(required = false) String district,
            @RequestParam(required = false) String sector,
            @RequestParam(required = false) String village,
            @RequestParam(required = false) Long provinceId) {
        return ResponseEntity.ok(locationService.update(id, cityName, district, sector, village, provinceId));
    }

    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(locationService.delete(id));
    }
}
