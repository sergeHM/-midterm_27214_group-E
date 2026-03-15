package com.hotel.repository;

import com.hotel.entity.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {
    boolean existsByProvinceCode(String provinceCode);
    boolean existsByProvinceName(String provinceName);
    Optional<Province> findByProvinceCode(String provinceCode);
    Optional<Province> findByProvinceName(String provinceName);
}
