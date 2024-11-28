package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.KhachHang;

import jakarta.transaction.Transactional;

import java.util.List;

public interface GioHangDAO extends JpaRepository<GioHang, Integer> {
    List<GioHang> findAllByKhachHangAndChecked(KhachHang khachHang, boolean checked);

    @Transactional
    void deleteByKhachHangAndCheckedTrue(KhachHang khachHang);
}
