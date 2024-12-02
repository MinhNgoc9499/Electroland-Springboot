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
        WHERE (:hoTen IS NULL OR kh.hoTen LIKE %:hoTen%)
          AND (:sdt IS NULL OR kh.sdt LIKE %:sdt%)
          AND (:email IS NULL OR kh.email LIKE %:email%)
          AND (:diaChi IS NULL OR dc.chiTiet LIKE %:diaChi%)
          AND (:trangThai IS NULL OR kh.trangThai = :trangThai)
        GROUP BY kh.id, kh.hoTen, kh.email, kh.sdt, kh.ngaySinh, kh.gioiTinh, kh.trangThai
    """, 
    countQuery = """
        SELECT COUNT(DISTINCT kh.id)
        FROM KhachHang kh
        LEFT JOIN DiaChi dc ON kh.id = dc.idKH
        WHERE (:hoTen IS NULL OR kh.hoTen LIKE %:hoTen%)
          AND (:sdt IS NULL OR kh.sdt LIKE %:sdt%)
          AND (:email IS NULL OR kh.email LIKE %:email%)
          AND (:diaChi IS NULL OR dc.chiTiet LIKE %:diaChi%)
          AND (:trangThai IS NULL OR kh.trangThai = :trangThai)
    """,
    nativeQuery = true)
    Page<Object[]> findFilteredCustomers(
        @org.springframework.data.repository.query.Param("hoTen") String hoTen,
        @org.springframework.data.repository.query.Param("sdt") String sdt,
        @org.springframework.data.repository.query.Param("email") String email,
        @org.springframework.data.repository.query.Param("diaChi") String diaChi,
        @org.springframework.data.repository.query.Param("trangThai") Boolean trangThai,
        Pageable pageable);
    
}
