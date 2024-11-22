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
public class LoaiKhachHang {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Định nghĩa cột tenLoai là nvarchar(225)
	@Column(columnDefinition = "nvarchar(225)", nullable = false)
	String tenLoai;
}
