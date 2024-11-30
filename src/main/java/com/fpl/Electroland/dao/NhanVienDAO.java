package com.fpl.Electroland.dao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpl.Electroland.model.NhanVien;

public interface NhanVienDAO extends JpaRepository<NhanVien, Integer> {

    @Query("SELECT nv FROM NhanVien nv WHERE str(nv.id) LIKE %:id%")
    List<NhanVien> findByIdLike(String id);

}
