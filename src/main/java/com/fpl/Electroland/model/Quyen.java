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
public class Quyen {

	// Khóa chính với giá trị tự động tăng
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Trường tên quyền, nullable = false, đảm bảo trường này không bao giờ là NULL
	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String ten;

	// Trường mô tả quyền, nullable = true, có thể NULL nếu không có mô tả
	@Column(nullable = true, columnDefinition = "nvarchar(225)")
	String moTa;
}
