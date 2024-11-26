package com.fpl.Electroland.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChiTietDh {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // Primary Key

	@Column(nullable = false) // Đảm bảo cột này không null
	private int soLuong;

	@Column(nullable = false) // Đảm bảo cột này không null
	private Double giaBan;

	@ManyToOne
	@JoinColumn(name = "idSP", nullable = false) // Khóa ngoại liên kết với SanPham
	private SanPham sanPham;

	@Column(nullable = true, columnDefinition = "NVARCHAR(225)")
	String moTa;

	@ManyToOne
	@JoinColumn(name = "maGiamSP")
	private MaGiamSp maGiamSp;

	@ManyToOne
	@JoinColumn(name = "idDH", nullable = false) // Khóa ngoại liên kết với DonHang
	private DonHang donHang;
}
