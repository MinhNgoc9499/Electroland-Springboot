package com.fpl.Electroland.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpl.Electroland.model.DonHang;



public interface DonHangDAO extends JpaRepository<DonHang, Integer> {
    DonHang findById(int id);
    //Tình người dùng bằng đơn hàng
     // Truy vấn tổng số đơn hàng theo tháng
    @Query("SELECT COUNT(d) FROM DonHang d WHERE MONTH(d.ngayDH) = :month")
    Long countTotalOrdersByMonth(@Param("month") int month);
     // Truy vấn tổng số giao dịch thành công theo tháng
     @Query("SELECT COUNT(d) FROM DonHang d WHERE d.trangThai = 1 AND MONTH(d.ngayDH) = :month")
     Long countSuccessfulTransactionsByMonth(@Param("month") int month);
     
    // Truy vấn tổng số đơn bị hủy theo tháng
    @Query("SELECT COUNT(d) FROM DonHang d WHERE d.trangThai = 0 AND MONTH(d.ngayDH) = :month")
    Long countCanceledOrdersByMonth(@Param("month") int month);

}