package com.fpl.Electroland.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.SanPham;

import jakarta.transaction.Transactional;

public interface GioHangDAO extends JpaRepository<GioHang, Integer> {
    List<GioHang> findAllByKhachHangAndChecked(KhachHang khachHang, boolean checked);

    @Transactional
    void deleteByKhachHangAndCheckedTrue(KhachHang khachHang);

    GioHang findBySanPhamAndKhachHang(SanPham sanPham, KhachHang khachHang);

    // Lấy danh sách giỏ hàng của một khách hàng
    List<GioHang> findByKhachHang(KhachHang khachHang);

    // Tìm giỏ hàng theo sản phẩm, khách hàng, mô tả
    Optional<GioHang> findBySanPhamAndMoTaAndKhachHang(SanPham sanPham, String moTa, KhachHang khachHang);
}
