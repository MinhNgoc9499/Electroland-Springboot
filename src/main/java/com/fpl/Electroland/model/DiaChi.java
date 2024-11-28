package com.fpl.Electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaChi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // Primary Key

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)") // Đảm bảo cột này không null và sử dụng NVARCHAR(225)
	private String chiTiet;

	@Column(nullable = true) // Đảm bảo cột này không null
	private boolean macDinh;

	@ManyToOne
	@JoinColumn(name = "idKH", nullable = false) // Khóa ngoại liên kết với KhachHang
	private KhachHang khachHang;

	@Column(nullable = true, columnDefinition = "NVARCHAR(100)") // Thêm cột loaiDiaChi kiểu String
	private String loaiDiaChi;

	@Column(nullable = true, columnDefinition = "NVARCHAR(100)") // Thêm cột hoTen kiểu String
	private String hoTenNN; // viết tắt của 'hoTenNguoiNhan'

	@Column(nullable = true, columnDefinition = "NVARCHAR(100)") // Thêm cột sdt kiểu String
	private String sdtNN; // viết tắt của 'sdtNguoiNhan'

}