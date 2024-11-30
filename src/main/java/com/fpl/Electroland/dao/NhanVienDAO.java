package com.fpl.Electroland.dao;

import com.fpl.Electroland.model.NhanVien;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;


public interface NhanVienDAO extends JpaRepository<NhanVien, Integer> {


    Optional<NhanVien> findByEmail(String email);
}
