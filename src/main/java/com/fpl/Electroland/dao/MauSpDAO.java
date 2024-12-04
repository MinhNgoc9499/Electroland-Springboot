package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpl.Electroland.model.Mau;
import com.fpl.Electroland.model.MauSp;
import com.fpl.Electroland.model.SanPham;

import java.util.List;
import java.util.Optional;

public interface MauSpDAO extends JpaRepository<MauSp, Integer> {
    List<MauSp> findBySanPham(SanPham sanPham);

    @Query("SELECT m.mau.tenMau FROM MauSp m WHERE m.sanPham = :sanPham")
    List<String> findTenMauBySanPham(SanPham sanPham);

    Optional<MauSp> findByMauAndSanPham(Mau mau, SanPham sp);
}
