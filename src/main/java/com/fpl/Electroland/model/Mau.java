package com.fpl.Electroland.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mau {

	// Khóa chính với giá trị tự động tăng
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Cột tenMau lưu trữ tên màu, sử dụng nvarchar(225) cho chuỗi Unicode, không
	// thể
	// NULL
	@Column(columnDefinition = "nvarchar(225)", nullable = false) // Đảm bảo không thể null
	String tenMau;
}
