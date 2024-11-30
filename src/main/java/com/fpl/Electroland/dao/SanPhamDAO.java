package com.fpl.Electroland.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.SanPham;

public interface SanPhamDAO extends JpaRepository<SanPham, Integer>, JpaSpecificationExecutor<SanPham>{
    List<SanPham> findByLoaiSanPham(LoaiSanPham loaiSanPham);
}
