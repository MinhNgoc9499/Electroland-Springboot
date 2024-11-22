package com.fpl.Electroland.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhaCungCap {

	// Khóa chính với giá trị tự động tăng
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Tên nhà cung cấp, không thể là NULL và sử dụng NVARCHAR(225)
	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String tenNhaCC;

	// Logo của nhà cung cấp, không thể là NULL và sử dụng NVARCHAR(225)
	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String logo;
}
