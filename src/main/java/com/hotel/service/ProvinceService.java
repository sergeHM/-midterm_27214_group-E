package com.hotel.service;

import com.hotel.entity.Province;
import com.hotel.repository.ProvinceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProvinceService {

    private final ProvinceRepository provinceRepository;

    
    @Transactional
    public Province save(String code, String name) {
        if (provinceRepository.existsByProvinceCode(code)) {
            throw new RuntimeException("Province with code '" + code + "' already exists.");
        }
        return provinceRepository.save(
            Province.builder().provinceCode(code).provinceName(name).build()
        );
    }

    
    public List<Province> getAll() {
        return provinceRepository.findAll();
    }

    
    public Province getById(Long id) {
        return provinceRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Province not found with id: " + id));
    }

    
    public Province getByCode(String code) {
        return provinceRepository.findByProvinceCode(code)
            .orElseThrow(() -> new RuntimeException("Province not found with code: " + code));
    }

    
    @Transactional
    public Province update(Long id, String newCode, String newName) {
        Province province = getById(id);
        if (newCode != null && !newCode.isBlank()) province.setProvinceCode(newCode);
        if (newName != null && !newName.isBlank()) province.setProvinceName(newName);
        return provinceRepository.save(province);
    }

    
    @Transactional
    public String delete(Long id) {
        Province province = getById(id);
        provinceRepository.delete(province);
        return "Province '" + province.getProvinceName() + "' deleted successfully.";
    }
}
