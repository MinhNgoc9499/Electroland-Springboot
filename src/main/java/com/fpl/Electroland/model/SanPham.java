package com.fpl.Electroland.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SanPham {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Trường tên sản phẩm, nullable = false và columnDefinition là nvarchar(225)
	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String tenSP;

	// Trường hình đại diện, nullable = false và columnDefinition là varchar(225)
	@Column(nullable = false, columnDefinition = "varchar(225)")
	String hinhDaiDien;

	// Trường mô tả sản phẩm, nullable = true nếu không cần thiết
	@Column(nullable = true, columnDefinition = "nvarchar(225)")
	String moTa;

	// Trường giá bán, nullable = false
	@Column(nullable = false)
	Double giaBan;

	// Trường giá giảm, nullable = true (có thể bỏ trống nếu không có)
	@Column(nullable = true)
	Double giaGiam;

	// Trường trạng thái, nullable = false
	@Column(nullable = false)
	Boolean trangThai = true;

	// Quan hệ với loại sản phẩm
	@ManyToOne
	@JoinColumn(name = "idLoaiSP", nullable = false)
	LoaiSanPham loaiSanPham;

	// Quan hệ với nhà cung cấp
	@ManyToOne
	@JoinColumn(name = "idNhaCC", nullable = false)
	NhaCungCap nhaCungCap;
}
