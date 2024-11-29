package com.fpl.Electroland.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaGiamSp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Đảm bảo giá trị giảm giá không thể có giá trị null và kiểu Double
	@Column(nullable = false) // nullable = false
	Double giaTri;

	@ManyToOne
	@JoinColumn(name = "idSP", nullable = false) // nullable = false
	SanPham sanPham;

	// Đảm bảo moTa là nvarchar(225) và không thể null
	@Column(columnDefinition = "nvarchar(225)", nullable = true)
	String moTa;
}
