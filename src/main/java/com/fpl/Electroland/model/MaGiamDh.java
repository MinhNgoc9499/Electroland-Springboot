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
public class MaGiamDh {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Đảm bảo giamGiaVND có thể là null
	@Column(nullable = true)
	Double giamGiaVND;

	// Đảm bảo phanTramGG có thể là null
	@Column(nullable = true)
	Double phanTramGG;

	// Đảm bảo maxGG có thể là null
	@Column(nullable = true)
	Double maxGG;

	// Đảm bảo minDonGia có thể là null
	@Column(nullable = true)
	Double minDonGia;

	// Đảm bảo moTa là nvarchar(225) và không thể null
	@Column(columnDefinition = "nvarchar(225)", nullable = false)
	String moTa;
}
