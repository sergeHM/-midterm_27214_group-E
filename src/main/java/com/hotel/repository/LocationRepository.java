package com.hotel.repository;

import com.hotel.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    Page<Location> findByProvinceId(Long provinceId, Pageable pageable);
    List<Location> findByProvinceId(Long provinceId);
    boolean existsByCityName(String cityName);
}
