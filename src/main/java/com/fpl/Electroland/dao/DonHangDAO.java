package com.fpl.Electroland.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.fpl.Electroland.model.DonHang;
import com.fpl.Electroland.model.KhachHang;
import java.util.List;


public interface DonHangDAO extends JpaRepository<DonHang, Integer> {
    DonHang findById(int id);
    //Tình người dùng bằng đơn hàng
    
}