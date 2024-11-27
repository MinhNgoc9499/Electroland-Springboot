package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpl.Electroland.model.MauSp;
import com.fpl.Electroland.model.SanPham;

import java.util.List;


public interface MauSpDAO extends JpaRepository<MauSp, Integer> {
    List<MauSp> findBySanPham(SanPham sanPham);
}
