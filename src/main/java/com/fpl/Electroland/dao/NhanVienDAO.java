package com.fpl.Electroland.dao;

import java.util.Collection;
import java.util.List;

import com.fpl.Electroland.model.NhanVien;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface NhanVienDAO extends JpaRepository<NhanVien, Integer> {

    @Query("SELECT nv FROM NhanVien nv WHERE str(nv.id) LIKE %:id%")
    List<NhanVien> findByIdLike(String id);

    Optional<NhanVien> findByEmail(String email);

    List<NhanVien> findByChucVu(String cv);

    List<NhanVien> findByTrangthai(Boolean tt);
}
