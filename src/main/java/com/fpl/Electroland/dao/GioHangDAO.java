package com.fpl.Electroland.dao;



import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;


import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.KhachHang;


public interface GioHangDAO extends JpaRepository<GioHang, Integer> {
    // Lấy danh sách giỏ hàng của một khách hàng
    List<GioHang> findByKhachHang(KhachHang khachHang);
}
