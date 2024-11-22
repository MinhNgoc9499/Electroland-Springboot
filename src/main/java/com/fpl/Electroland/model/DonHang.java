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
public class DonHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String diaChi, nguoiNhan, sdt, phuongThucTT;
	Date ngayDH, ngayGH;
	boolean trangThai;

	@ManyToOne
	@JoinColumn(name = "idKH")
	KhachHang khachHang;

	@ManyToOne
	@JoinColumn(name = "idMGKH")
	MaGiamKh maGiamKh;

}
