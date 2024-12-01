package com.fpl.Electroland.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import com.fpl.Electroland.model.SanPham;
import com.fpl.Electroland.model.ThuocTinh;
import com.fpl.Electroland.model.ThuocTinhSp;

public interface ThuocTinhDAO extends JpaRepository<ThuocTinh, Integer> {
    @Query("SELECT t.ten, tt.tenTT FROM ThuocTinh tt JOIN tt.thuocTinhSp t WHERE t.sanPham = :sanPham") 
    List<Object[]> findAttributesBySanPham(SanPham sanPham);

    List<ThuocTinh> findByThuocTinhSp(ThuocTinhSp thuocTinhSp);
}
