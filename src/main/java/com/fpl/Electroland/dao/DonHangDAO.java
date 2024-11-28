package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpl.Electroland.model.DonHang;
import java.util.List;

public interface DonHangDAO extends JpaRepository<DonHang, Integer> {

}
