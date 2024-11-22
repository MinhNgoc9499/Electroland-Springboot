package com.fpl.Electroland.model;

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
public class PhanQuyen {

	// Khóa chính với giá trị tự động tăng
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Liên kết với bảng nhân viên, nullable = false
	@ManyToOne
	@JoinColumn(name = "idNV", nullable = false)
	NhanVien nhanVien;

	// Liên kết với bảng quyền, nullable = false
	@ManyToOne
	@JoinColumn(name = "idQuyen", nullable = false)
	Quyen quyen;
}
