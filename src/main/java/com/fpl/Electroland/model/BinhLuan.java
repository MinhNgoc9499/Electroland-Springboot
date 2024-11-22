package com.fpl.Electroland.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BinhLuan {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // Primary Key

	@Column(nullable = false, columnDefinition = "NVARCHAR(255)") // Đảm bảo lưu trữ chuỗi Unicode, với độ dài tối đa //
																	// 255 ký tự
	private String noiDung;

	@Temporal(TemporalType.TIMESTAMP) // Sử dụng TIMESTAMP để lưu cả ngày và giờ
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(name = "ngayTao", nullable = false)
	private Date ngayTao = new Date(); // Ngày tạo với giá trị mặc định là ngày giờ hiện tại

	@ManyToOne
	@JoinColumn(name = "idKH", nullable = false) // Khóa ngoại liên kết với KhachHang, đảm bảo không null
	private KhachHang khachHang;

	@ManyToOne
	@JoinColumn(name = "idSP", nullable = false) // Khóa ngoại liên kết với SanPham, đảm bảo không null
	private SanPham sanPham;
}
