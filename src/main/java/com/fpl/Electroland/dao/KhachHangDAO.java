package com.fpl.Electroland.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.KhachHang;

public interface KhachHangDAO extends JpaRepository<KhachHang, Integer> {

	Optional<KhachHang> findByEmailAndMatKhau(String email, String matKhau);

	Optional<KhachHang> findByEmail(String email);
}
