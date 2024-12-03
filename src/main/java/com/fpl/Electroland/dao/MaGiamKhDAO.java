package com.fpl.Electroland.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.MaGiamKh;
import com.fpl.Electroland.model.MaGiamDh;
import com.fpl.Electroland.model.SanPham;

import jakarta.transaction.Transactional;

@Transactional
public interface MaGiamKhDAO extends JpaRepository<MaGiamKh, Integer> {
  List<MaGiamKh> findByKhachHangAndChecked(KhachHang khachHang, boolean checked);

  @Query("Select mgkh.maGiamDh from MaGiamKh mgkh where mgkh.khachHang = :khachHang and mgkh.checked = true and maGiamSp is null")
  MaGiamDh getMGDHChecked(KhachHang khachHang);

  @Query("Select mgkh from MaGiamKh mgkh where mgkh.khachHang = :khachHang and mgkh.checked = true and mgkh.maGiamSp.sanPham = :sanPham")
  List<MaGiamKh> getMGSPChecked(KhachHang khachHang, SanPham sanPham);

  @Transactional
  void deleteByKhachHangAndCheckedTrue(KhachHang khachHang);

  List<MaGiamKh> findByKhachHang(KhachHang khachHang);

  Optional<MaGiamKh> findByKhachHangAndMaGiamSpIsNullAndCheckedTrue(KhachHang khachhang);
}
