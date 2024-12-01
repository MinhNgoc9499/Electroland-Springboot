package com.fpl.Electroland.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.MaGiamKh;
import com.fpl.Electroland.model.MaGiamSp;


public interface MaGiamKhDAO extends JpaRepository<MaGiamKh, Integer> {
    // Lấy danh sách giỏ hàng của một khách hàng
    List<MaGiamKh> findByKhachHang(KhachHang khachHang);
    Optional<MaGiamKh> findByKhachHangAndMaGiamSpIsNullAndCheckedTrue(KhachHang khachhang);
}
