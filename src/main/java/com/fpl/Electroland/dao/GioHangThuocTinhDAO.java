package com.fpl.Electroland.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.GioHangThuocTinh;

import jakarta.transaction.Transactional;

@Transactional
public interface GioHangThuocTinhDAO extends JpaRepository<GioHangThuocTinh, Integer> {
    List<GioHangThuocTinh> findByGioHang(GioHang gioHang);

    @Modifying
    @Query("DELETE FROM GioHangThuocTinh g WHERE g.gioHang = :gioHang")
    void deleteAllByGioHang(@Param("gioHang") GioHang gioHang);

}
