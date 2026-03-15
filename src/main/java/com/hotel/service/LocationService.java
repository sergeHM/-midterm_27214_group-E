package com.hotel.service;

import com.hotel.entity.Location;
import com.hotel.entity.Province;
import com.hotel.repository.LocationRepository;
import com.hotel.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final LocationRepository locationRepository;
    private final ProvinceRepository provinceRepository;

    @Transactional
    public Location save(Long provinceId, String cityName, String district,
                         String sector, String village) {
        Province province = provinceRepository.findById(provinceId)
            .orElseThrow(() -> new RuntimeException("Province not found: " + provinceId));
        return locationRepository.save(
            Location.builder()
                .cityName(cityName)
                .district(district)
                .sector(sector)
                .village(village)
                .province(province)
                .build()
        );
    }

    public Page<Location> getAll(int page, int size, String sortBy) {
        return locationRepository.findAll(PageRequest.of(page, size, Sort.by(sortBy)));
    }

    public Location getById(Long id) {
        return locationRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Location not found: " + id));
    }

    public List<Location> getByProvince(Long provinceId) {
        return locationRepository.findByProvinceId(provinceId);
    }

    @Transactional
    public Location update(Long id, String cityName, String district,
                           String sector, String village, Long provinceId) {
        Location loc = getById(id);
        if (cityName != null && !cityName.isBlank())   loc.setCityName(cityName);
        if (district != null && !district.isBlank())   loc.setDistrict(district);
        if (sector != null && !sector.isBlank())       loc.setSector(sector);
        if (village != null && !village.isBlank())     loc.setVillage(village);
        if (provinceId != null) {
            Province p = provinceRepository.findById(provinceId)
                .orElseThrow(() -> new RuntimeException("Province not found: " + provinceId));
            loc.setProvince(p);
        }
        return locationRepository.save(loc);
    }

    @Transactional
    public String delete(Long id) {
        Location loc = getById(id);
        locationRepository.delete(loc);
        return "Location " + id + " deleted successfully.";
    }
}
