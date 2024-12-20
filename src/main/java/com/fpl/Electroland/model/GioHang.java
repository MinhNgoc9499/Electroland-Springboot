package com.fpl.Electroland.model;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table
public class GioHang {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Số lượng sản phẩm
	@Column(nullable = false)
	int soLuong;

	@Column(nullable = true, columnDefinition = "nvarchar(225)")
	String moTa;

	@Column(nullable = true)
	Boolean checked;

	// Mối quan hệ với sản phẩm
	@ManyToOne
	@JoinColumn(name = "idSP", nullable = false)
	SanPham sanPham;

	// Mối quan hệ với khách hàng
	@ManyToOne
	@JoinColumn(name = "idKH", nullable = false)
	KhachHang khachHang;

}
