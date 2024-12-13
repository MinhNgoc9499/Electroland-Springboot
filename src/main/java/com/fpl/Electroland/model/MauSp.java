package com.fpl.Electroland.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "idSP", "idMau" }))
public class MauSp {

	// Khóa chính với giá trị tự động tăng
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	Double giaTri = 0.0;

	// Quan hệ Many-to-One với SanPham, mỗi sản phẩm có thể có nhiều màu
	@ManyToOne
	@JoinColumn(name = "idSP", nullable = false) // Liên kết với cột idSP trong bảng SanPham
	SanPham sanPham;

	// Quan hệ Many-to-One với Mau, mỗi màu có thể thuộc nhiều sản phẩm
	@ManyToOne
	@JoinColumn(name = "idMau", nullable = false) // Liên kết với cột idMau trong bảng Mau
	Mau mau;
}
