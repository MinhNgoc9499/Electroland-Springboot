package com.fpl.Electroland.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpl.Electroland.model.ChiTietDh;
import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.SanPham;

public interface ChiTietDhDAO extends JpaRepository<ChiTietDh, Integer> {
    @Query("SELECT d.id AS donHangId, d.ngayDH AS ngayDatHang, d.trangThai AS trangThai, " +
            "SUM(COALESCE(c.soLuong, 0) * COALESCE(c.giaBan, 0)) AS tongGiaTri " +
            "FROM DonHang d " +
            "LEFT JOIN ChiTietDh c ON d.id = c.donHang.id " +
            "WHERE d.khachHang.id = :idKhachHang AND d.trangThai = :trangThai " +
            "GROUP BY d.id, d.ngayDH, d.trangThai")
    List<Object[]> findOrdersByCustomerIdAndStatus(@Param("idKhachHang") int idKhachHang,
            @Param("trangThai") int trangThai);

    @Query("SELECT c FROM ChiTietDh c WHERE c.donHang.id = :orderId")
    List<ChiTietDh> findByDonHangId(@Param("orderId") int orderId);

    // Tìm người dùng qa chi tiết đơn hàng
    @Query("SELECT c FROM ChiTietDh c WHERE c.donHang.khachHang = :khachHang AND c.sanPham = :sanPham")
    List<ChiTietDh> findSanPhamDaMuaByKhachHang(KhachHang khachHang, SanPham sanPham);
}