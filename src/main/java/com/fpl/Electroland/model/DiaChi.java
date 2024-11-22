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

	@Column(nullable = false, columnDefinition = "VARCHAR(50)") // Đảm bảo cột này không null và sử dụng NVARCHAR(225)
	private String toaDo;

	@Column(nullable = false) // Đảm bảo cột này không null
	private Double khoangCach;

	@Column(nullable = false) // Đảm bảo cột này không null
	private boolean macDinh;

	@ManyToOne
	@JoinColumn(name = "idKH", nullable = false) // Khóa ngoại liên kết với KhachHang
	private KhachHang khachHang;
}
