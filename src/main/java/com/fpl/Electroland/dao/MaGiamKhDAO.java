package com.fpl.Electroland.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.MaGiamKh;

public interface MaGiamKhDAO extends JpaRepository<MaGiamKh, Integer> {
        // Lấy danh sách giỏ hàng của một khách hàng
    List<MaGiamKh> findByKhachHang(KhachHang khachHang);

}
