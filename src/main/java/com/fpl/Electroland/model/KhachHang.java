package com.fpl.Electroland.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

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
public class KhachHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String hoTen;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date ngaySinh;
	String std;
	Boolean gioiTinh;
	String email, matKhau;
	Boolean trangThai;

	@ManyToOne
	@JoinColumn(name = "idLoaiKH")
	private LoaiKhachHang loaiKhachHang;

	@Override
	public String toString() {
		return "KhachHang [id=" + id + ", hoTen=" + hoTen + ", ngaySinh=" + ngaySinh + ", std=" + std + ", gioiTinh="
				+ gioiTinh + ", email=" + email + ", matKhau=" + matKhau + ", trangThai=" + trangThai
				+ ", loaiKhachHang=" + loaiKhachHang + "]";
	}

}