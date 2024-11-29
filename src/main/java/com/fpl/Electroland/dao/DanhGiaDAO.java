package com.fpl.Electroland.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpl.Electroland.model.DanhGia;

public interface DanhGiaDAO extends JpaRepository<DanhGia, Integer> {
    @Query("SELECT SUM(d.diem) FROM DanhGia d") 
    Integer sumOfDanhGia();
    //Tìm đánh giá bằng sản phẩm
    @Query("SELECT dg FROM DanhGia dg WHERE dg.sanPham.id = :sanPhamId") 
    List<DanhGia> findBySanPhamId(int sanPhamId);
    
}
