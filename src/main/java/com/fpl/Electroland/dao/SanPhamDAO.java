package com.fpl.Electroland.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.SanPham;

public interface SanPhamDAO extends JpaRepository<SanPham, Integer>, JpaSpecificationExecutor<SanPham> {
    List<SanPham> findByLoaiSanPham(LoaiSanPham loaiSanPham);

    @Query("SELECT sp FROM SanPham sp WHERE (:categoryId IS NULL OR sp.loaiSanPham.id = :categoryId)")
    List<SanPham> findByIdLoaiSP(Integer categoryId);

    @Query("SELECT sp FROM SanPham sp ORDER BY CASE WHEN :sortByPrice = 'asc' THEN sp.giaBan END ASC, CASE WHEN :sortByPrice = 'desc' THEN sp.giaBan END DESC")
    List<SanPham> findBySortByPrice(String sortByPrice);

    List<SanPham> findByTrangThai(Boolean trangThai);
}
