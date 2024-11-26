package com.fpl.Electroland.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHang {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // Primary Key

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)") // Sử dụng NVARCHAR(225)
	private String diaChi;

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)") // Sử dụng NVARCHAR(225)
	private String nguoiNhan;

	@Column(nullable = false, columnDefinition = "VARCHAR(15)") // Sử dụng VARCHAR(15)
	private String sdt;

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)") // Sử dụng NVARCHAR(225)
	private String phuongThucTT;

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)") // Sử dụng NVARCHAR(225)
	private String ghiChu;

	@Temporal(TemporalType.TIMESTAMP) // Lưu cả ngày và giờ
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") // Định dạng ngày tháng năm giờ:phút
	@Column(nullable = false)
	private Date ngayDH = new Date(); // Ngày đơn hàng

	@Temporal(TemporalType.TIMESTAMP) // Lưu cả ngày và giờ
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm") // Định dạng ngày tháng năm giờ:phút
	@Column(nullable = true)
	private Date ngayGH; // Ngày giao hàng

	@Column(nullable = false) // Trạng thái không được null
	private int trangThai = 2;

	@ManyToOne
	@JoinColumn(name = "maGiamDH", nullable = true) // Khóa ngoại với MaGiamDh
	private MaGiamDh maGiamDh;

	@ManyToOne
	@JoinColumn(name = "idKH", nullable = false) // Khóa ngoại với KhachHang
	private KhachHang khachHang;
}
