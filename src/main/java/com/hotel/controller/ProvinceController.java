package com.hotel.controller;

import com.hotel.entity.Province;
import com.hotel.service.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/provinces")
@RequiredArgsConstructor
public class ProvinceController {

    private final ProvinceService provinceService;

    
    
    @PostMapping
    public ResponseEntity<Province> create(@RequestParam String provinceCode,
                                           @RequestParam String provinceName) {
        return ResponseEntity.ok(provinceService.save(provinceCode, provinceName));
    }

    
    
    @GetMapping
    public ResponseEntity<List<Province>> getAll() {
        return ResponseEntity.ok(provinceService.getAll());
    }

    
    
    @GetMapping("/{id}")
    public ResponseEntity<Province> getById(@PathVariable Long id) {
        return ResponseEntity.ok(provinceService.getById(id));
    }

    
    
    @GetMapping("/code/{code}")
    public ResponseEntity<Province> getByCode(@PathVariable String code) {
        return ResponseEntity.ok(provinceService.getByCode(code));
    }

    
    
    @PutMapping("/{id}")
    public ResponseEntity<Province> update(@PathVariable Long id,
                                           @RequestParam(required = false) String provinceCode,
                                           @RequestParam(required = false) String provinceName) {
        return ResponseEntity.ok(provinceService.update(id, provinceCode, provinceName));
    }

    
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        return ResponseEntity.ok(provinceService.delete(id));
    }
}
