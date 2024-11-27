package com.fpl.Electroland.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "idSP", "idKH", "moTa" }))
public class GioHang {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Số lượng sản phẩm
	@Column(nullable = false)
	int soLuong;

	// Mối quan hệ với sản phẩm
	@ManyToOne
	@JoinColumn(name = "idSP", nullable = false)
	SanPham sanPham;

	@Column(nullable = true, columnDefinition = "NVARCHAR(225)")
	String moTa;

	// Mối quan hệ với khách hàng
	@ManyToOne
	@JoinColumn(name = "idKH", nullable = false)
	KhachHang khachHang;
	boolean checked;
}
