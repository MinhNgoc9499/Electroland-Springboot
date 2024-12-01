package com.fpl.Electroland.dao;


import com.fpl.Electroland.model.DonHang;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


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

    // @Query("Select new com.fpl.Electroland.dto.DonHangStatDTO( " +

    // List<DonHangStatDTO> findDonHangStats();

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