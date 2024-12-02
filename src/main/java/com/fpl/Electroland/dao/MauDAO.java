package com.fpl.Electroland.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpl.Electroland.model.Mau;
import com.fpl.Electroland.model.SanPham;

public interface MauDAO extends JpaRepository<Mau, Integer> {
    
    @Query("SELECT msp.mau FROM MauSp msp WHERE msp.sanPham = :sanPham") 
    List<Mau> findMauBySanPham(@Param("sanPham") SanPham sanPham);
}
