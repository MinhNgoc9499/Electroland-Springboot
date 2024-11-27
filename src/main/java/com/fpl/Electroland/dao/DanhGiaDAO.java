package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpl.Electroland.model.DanhGia;

public interface DanhGiaDAO extends JpaRepository<DanhGia, Integer> {
    @Query("SELECT SUM(d.diem) FROM DanhGia d") 
    Integer sumOfDanhGia();
}
