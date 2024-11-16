package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fpl.Electroland.model.LoaiSanPham;

@Repository
public interface LoaiSanPhamDAO extends JpaRepository<LoaiSanPham, Integer> {

}
