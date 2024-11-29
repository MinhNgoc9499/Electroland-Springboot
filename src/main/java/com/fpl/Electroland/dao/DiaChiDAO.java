package com.fpl.Electroland.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpl.Electroland.model.DiaChi;
import com.fpl.Electroland.model.KhachHang;

import java.util.List;

import jakarta.transaction.Transactional;

public interface DiaChiDAO extends JpaRepository<DiaChi, Integer> {
    // Lấy tất cả địa chỉ của khách hàng
    List<DiaChi> findByKhachHangId(int idKhachHang);

    @Modifying
    @Transactional
    @Query("DELETE FROM DiaChi d WHERE d.id IN :ids")
    void deleteByIdIn(@Param("ids") List<Long> ids);

    List<DiaChi> findByKhachHang(KhachHang khachHang);

    DiaChi findByKhachHangAndMacDinhTrue(KhachHang khachHang);
}
