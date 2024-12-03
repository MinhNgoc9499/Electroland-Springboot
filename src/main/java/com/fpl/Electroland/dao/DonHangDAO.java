package com.fpl.Electroland.dao;

import java.util.Date;
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

        // Tình người dùng bằng đơn hàng
        // Truy vấn tổng số đơn hàng theo tháng
        @Query("SELECT COUNT(d) FROM DonHang d WHERE MONTH(d.ngayDH) = :month")
        Long countTotalOrdersByMonth(@Param("month") int month);

        // Truy vấn tổng số giao dịch thành công theo tháng
        @Query("SELECT COUNT(d) FROM DonHang d WHERE d.trangThai = 1 AND MONTH(d.ngayDH) = :month")
        Long countSuccessfulTransactionsByMonth(@Param("month") int month);

        // Truy vấn tổng số đơn bị hủy theo tháng
        @Query("SELECT COUNT(d) FROM DonHang d WHERE d.trangThai = 0 AND MONTH(d.ngayDH) = :month")
        Long countCanceledOrdersByMonth(@Param("month") int month);

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

        @Query("SELECT d FROM DonHang d WHERE MONTH(d.ngayDH) = :month AND YEAR(d.ngayDH) = :year")
        List<DonHang> findByMonthYear(int month, @Param("year") int year);

        @Query(value = "SELECT SUM(cth.soLuong * cth.giaBan) FROM DonHang dh JOIN ChiTietDH cth ON dh.id = cth.idDH WHERE dh.trangThai = 1 AND MONTH(dh.ngayDH) = :month AND YEAR(dh.ngayDH) = :year", nativeQuery = true)
        Double sumTotalSalesByYear(int month, @Param("year") int year);

        @Query("SELECT kh FROM KhachHang kh WHERE (SELECT COUNT(d) FROM DonHang d WHERE d.khachHang.id = kh.id) >= :minOrders AND (:sortTypeKH IS NULL OR kh.loaiKhachHang.tenLoai = :sortTypeKH)")
        Page<KhachHang> findCustomersWithMoreThanMinOrders(int minOrders, String sortTypeKH, Pageable pageable);

        @Query("SELECT COUNT(d) FROM DonHang d WHERE d.khachHang.id = :customerId")
        Integer countOrdersByCustomer(int customerId);

        @Query("SELECT SUM(ct.giaBan * ct.soLuong) FROM DonHang d INNER JOIN ChiTietDh ct ON ct.donHang.id = d.id WHERE d.khachHang.id = :customerId")
        Double sumTotalSalesByCustomer(int customerId);

        // THống kê sp WHERE sp.loaiSanPham.id = :sortTypeSP String sortTypeSP,
        @Query("SELECT DISTINCT sp FROM SanPham sp INNER JOIN ChiTietDh ct ON sp.id = ct.sanPham.id INNER JOIN DonHang dh ON ct.donHang.id = dh.id WHERE (:sortTypeSP IS NULL OR sp.loaiSanPham.id = :sortTypeSP) AND (:search IS NULL OR sp.tenSP LIKE %:search%)")
        Page<SanPham> findRevenueByProductType(String search, Integer sortTypeSP, Pageable pageable);
 

        @Query("SELECT COUNT(d) FROM ChiTietDh d WHERE d.sanPham.id = :productID")
        Integer countOrderProdcut(int productID);

        @Query("SELECT SUM(d.giaBan * d.soLuong) FROM ChiTietDh d INNER JOIN SanPham sp ON d.sanPham.id = sp.id WHERE d.sanPham.id = :productID")
        Double sumTotalByProduct(int productID);

        @Query("SELECT d FROM DonHang d WHERE MONTH(d.ngayDH) = :month AND YEAR(d.ngayDH) = :year AND d.trangThai = :trangThai")
        List<DonHang> findByMonthYearAndTrangThai(int month, int year, int trangThai);

        @Query(value = """
                        SELECT * FROM DonHang
                        WHERE
                            (:searchOrderId = '' OR id = :searchOrderId)
                            AND (:searchCustomerName = '' OR nguoiNhan LIKE %:searchCustomerName%)
                            AND (:searchPhoneNumber = '' OR sdt LIKE %:searchPhoneNumber%)
                            AND (:searchAddress = '' OR diaChi LIKE %:searchAddress%)
                            AND (:paymentMethod = '' OR phuongThucTT LIKE %:paymentMethod%)
                            AND (:status = '' OR trangThai =:status)
                        """, nativeQuery = true)
        Page<DonHang> pageDonHang(String searchOrderId, String searchCustomerName, String searchPhoneNumber,
                        String searchAddress, String paymentMethod, String status, Pageable pageable);
}