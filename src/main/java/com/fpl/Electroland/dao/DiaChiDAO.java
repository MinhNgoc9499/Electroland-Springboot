package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpl.Electroland.model.DiaChi;

public interface DiaChiDAO extends JpaRepository<DiaChi, Integer> {
 // Lấy tất cả địa chỉ của khách hàng
 List<DiaChi> findByKhachHangId(int idKhachHang);
}
