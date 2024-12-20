package com.fpl.Electroland.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpl.Electroland.model.SanPham;
import com.fpl.Electroland.model.ThuocTinhSp;

public interface ThuocTinhSpDAO extends JpaRepository<ThuocTinhSp, Integer> {
    List<ThuocTinhSp> findBySanPham(SanPham sanPham);

    Optional<ThuocTinhSp> findBySanPhamAndTenLike(SanPham sanPham, String ten);
}
