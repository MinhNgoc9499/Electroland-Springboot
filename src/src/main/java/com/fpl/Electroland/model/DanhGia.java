package com.fpl.Electroland.model;


import java.util.Date;

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
public class DanhGia {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		int id;
		int diem;
		Date ngayTao;
		
		@ManyToOne
		@JoinColumn(name = "idKH")
		KhachHang khachHang;
		
		@ManyToOne
		@JoinColumn(name = "idSP")
		SanPham sanPham;
}
