package com.fpl.Electroland.model;

import org.hibernate.annotations.ManyToAny;

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
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"idSP","idKH"}))
public class GioHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	int soLuong;
	
	@ManyToOne
	@JoinColumn(name = "idSP")
	SanPham sanPham;
	
	@ManyToOne
	@JoinColumn(name = "idKH")
	KhachHang khachHang;
}
