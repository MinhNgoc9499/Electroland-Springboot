package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpl.Electroland.model.DiaChi;
import com.fpl.Electroland.model.KhachHang;

import java.util.List;

public interface DiaChiDAO extends JpaRepository<DiaChi, Integer> {
    List<DiaChi> findByKhachHang(KhachHang khachHang);

    DiaChi findByKhachHangAndMacDinhTrue(KhachHang khachHang);
}
