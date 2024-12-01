package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpl.Electroland.model.DonHang;
import java.util.List;

public interface DonHangDAO extends JpaRepository<DonHang, Integer> {
    // Truy vấn đơn hàng theo id
    DonHang findById(int id);

    // Truy vấn tổng số đơn hàng theo năm và tháng
    @Query("SELECT COUNT(d) FROM DonHang d WHERE YEAR(d.ngayDH) = :year AND MONTH(d.ngayDH) = :month")
    Long tinhTongDonHangTheoNamvaThang(@Param("year") int year, @Param("month") int month);

    // Truy vấn tổng số giao dịch thành công theo năm và tháng
    @Query("SELECT COUNT(d) FROM DonHang d WHERE d.trangThai = 1 AND YEAR(d.ngayDH) = :year AND MONTH(d.ngayDH) = :month")
    Long tinhDonHangthanhcongTheoNamvaThang(@Param("year") int year, @Param("month") int month);

    // Truy vấn tổng số đơn bị hủy theo năm và tháng
    @Query("SELECT COUNT(d) FROM DonHang d WHERE d.trangThai = 0 AND YEAR(d.ngayDH) = :year AND MONTH(d.ngayDH) = :month")
    Long tinhDonHangBiHuyTheoNamvaThang(@Param("year") int year, @Param("month") int month);

    @Query(" SELECT SUM(d.tongTien) FROM DonHang d WHERE YEAR(d.ngayDH) = :year AND MONTH(d.ngayDH) = :month")
    Long getRevenueByMonthAndYear(int year, int month);

    @Query("SELECT " +
            "CASE " +
            "WHEN MONTH(d.ngayDH) IN (1, 2, 3) THEN 'Quý 1' " +
            "WHEN MONTH(d.ngayDH) IN (4, 5, 6) THEN 'Quý 2' " +
            "WHEN MONTH(d.ngayDH) IN (7, 8, 9) THEN 'Quý 3' " +
            "WHEN MONTH(d.ngayDH) IN (10, 11, 12) THEN 'Quý 4' " +
            "END AS Quy, " +
            "COUNT(d.id) AS tongDonHang " +
            "FROM DonHang d " +
            "WHERE YEAR(d.ngayDH) = :year " +
            "GROUP BY " +
            "CASE " +
            "WHEN MONTH(d.ngayDH) IN (1, 2, 3) THEN 'Quý 1' " +
            "WHEN MONTH(d.ngayDH) IN (4, 5, 6) THEN 'Quý 2' " +
            "WHEN MONTH(d.ngayDH) IN (7, 8, 9) THEN 'Quý 3' " +
            "WHEN MONTH(d.ngayDH) IN (10, 11, 12) THEN 'Quý 4' " +
            "END ")
    List<Object[]> findTotalOrdersByQuarter(@Param("year") int year);

    @Query("SELECT MONTH(d.ngayDH) AS month, COALESCE(SUM(d.tongTien), 0) AS revenue " +
            "FROM DonHang d " +
            "WHERE YEAR(d.ngayDH) = :year " +
            "GROUP BY MONTH(d.ngayDH) " +
            "ORDER BY MONTH(d.ngayDH)")
    List<Object[]> getRevenueByMonth(@Param("year") int year);

}