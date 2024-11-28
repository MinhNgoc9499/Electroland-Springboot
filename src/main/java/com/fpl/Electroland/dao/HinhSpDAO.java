package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpl.Electroland.model.HinhSp;
import com.fpl.Electroland.model.SanPham;

import java.util.List;


public interface HinhSpDAO extends JpaRepository<HinhSp, Integer> {
    List<HinhSp> findBySanPham(SanPham sanPham);
}
