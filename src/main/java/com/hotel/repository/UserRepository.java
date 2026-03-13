package com.hotel.repository;

import com.hotel.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);

    Page<User> findAll(Pageable pageable);

    
    @Query("SELECT u FROM User u JOIN u.location l JOIN l.province p " +
           "WHERE p.provinceCode = :code OR p.provinceName = :name")
    List<User> findByProvinceCodeOrName(@Param("code") String code, @Param("name") String name);

    @Query("SELECT u FROM User u JOIN u.location l JOIN l.province p " +
           "WHERE p.provinceCode = :code OR p.provinceName = :name")
    Page<User> findByProvinceCodeOrName(@Param("code") String code,
                                        @Param("name") String name, Pageable pageable);
}
