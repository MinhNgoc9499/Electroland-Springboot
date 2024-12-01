package com.fpl.Electroland.dao;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.fpl.Electroland.model.DonHang;
import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.SanPham;



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

    @Query("SELECT d FROM DonHang d WHERE MONTH(d.ngayDH) = :month AND YEAR(d.ngayDH) = :year")
    List<DonHang> findByMonthYear(int month,@Param("year") int year);

    @Query(value = "SELECT SUM(cth.soLuong * cth.giaBan) FROM DonHang dh JOIN ChiTietDH cth ON dh.id = cth.idDH WHERE dh.trangThai = 1 AND MONTH(dh.ngayDH) = :month AND YEAR(dh.ngayDH) = :year", nativeQuery = true)
    Double sumTotalSalesByYear(int month, @Param("year") int year);

    @Query("SELECT kh FROM KhachHang kh WHERE (SELECT COUNT(d) FROM DonHang d WHERE d.khachHang.id = kh.id) > :minOrders AND (:sortTypeKH IS NULL OR kh.loaiKhachHang.tenLoai = :sortTypeKH)")
    Page<KhachHang> findCustomersWithMoreThanMinOrders(int minOrders, String sortTypeKH, Pageable pageable);

    @Query("SELECT COUNT(d) FROM DonHang d WHERE d.khachHang.id = :customerId")
    Integer countOrdersByCustomer(int customerId);

    @Query("SELECT SUM(ct.giaBan * ct.soLuong) FROM DonHang d INNER JOIN ChiTietDh ct ON ct.donHang.id = d.id WHERE d.khachHang.id = :customerId")
    Double sumTotalSalesByCustomer(int customerId);


    //THống kê sp
    @Query("SELECT sp FROM SanPham sp INNER JOIN ChiTietDh ct ON sp.id = ct.sanPham.id")
    Page<SanPham> findRevenueByProduct(Pageable pageable);

    
    @Query("SELECT COUNT(d) FROM ChiTietDh d WHERE d.sanPham.id = :productID")
    Integer countOrderProdcut(int productID);

    @Query("SELECT SUM(d.giaBan * d.soLuong) FROM ChiTietDh d INNER JOIN SanPham sp ON d.sanPham.id = sp.id WHERE d.sanPham.id = :productID")
    Double sumTotalByProduct(int productID);
}