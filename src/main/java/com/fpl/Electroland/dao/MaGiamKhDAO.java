package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.MaGiamDh;
import com.fpl.Electroland.model.MaGiamKh;
import com.fpl.Electroland.model.MaGiamSp;
import com.fpl.Electroland.model.SanPham;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

public interface MaGiamKhDAO extends JpaRepository<MaGiamKh, Integer> {
    List<MaGiamKh> findByKhachHangAndSelected(KhachHang khachHang, boolean selected);

    @Query("Select mgkh.maGiamDh from MaGiamKh mgkh where mgkh.khachHang = :khachHang and mgkh.selected = true and maGiamSp is null")
    MaGiamDh getMGDHSelected(KhachHang khachHang);

    @Query("Select mgkh.maGiamSp from MaGiamKh mgkh where mgkh.khachHang = :khachHang and mgkh.selected = true and maGiamSp.sanPham = :sanPham")
    MaGiamSp getMGSPSelected(KhachHang khachHang, SanPham sanPham);

    @Transactional
    void deleteByKhachHangAndSelectedTrue(KhachHang khachHang);

    Optional<MaGiamKh> findByKhachHangAndMaGiamSpIsNullAndSelectedTrue(KhachHang khachhang);
}
