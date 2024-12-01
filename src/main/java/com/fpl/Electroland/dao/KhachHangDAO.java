package com.fpl.Electroland.dao;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.fpl.Electroland.model.KhachHang;

public interface KhachHangDAO extends JpaRepository<KhachHang, Integer> {

    Optional<KhachHang> findByEmailAndMatKhau(String email, String matKhau);

    Optional<KhachHang> findByEmail(String email);

    @Query(value = """
        SELECT kh.id, kh.hoTen, kh.email, kh.sdt, kh.ngaySinh, kh.gioiTinh, kh.trangThai,
               STRING_AGG(dc.chiTiet, ', ') AS diaChiList
        FROM KhachHang kh
        LEFT JOIN DiaChi dc ON kh.id = dc.idKH
        GROUP BY kh.id, kh.hoTen, kh.email, kh.sdt, kh.ngaySinh, kh.gioiTinh, kh.trangThai
    """, 
    countQuery = """
        SELECT COUNT(DISTINCT kh.id)
        FROM KhachHang kh
        LEFT JOIN DiaChi dc ON kh.id = dc.idKH
    """, 
    nativeQuery = true)
    Page<Object[]> findCustomersWithAddresses(Pageable pageable);
}
