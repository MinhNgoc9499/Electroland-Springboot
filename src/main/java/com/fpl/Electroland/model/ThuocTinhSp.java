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
public class ThuocTinhSp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Trường tên thuộc tính sản phẩm, nullable = false và columnDefinition là
	// nvarchar(225)
	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String ten;

	// Quan hệ với sản phẩm, nullable = false (mỗi thuộc tính sản phẩm phải liên kết
	// với một sản phẩm)
	@ManyToOne
	@JoinColumn(name = "idSP", nullable = false) // Điều này có nghĩa là mỗi ThuocTinhSp phải có SanPham
	SanPham sanPham;
}
