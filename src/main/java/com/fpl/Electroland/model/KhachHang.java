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
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class KhachHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String hoTen;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date ngaySinh;
	String sdt;
	Boolean gioiTinh;
	String email, matKhau;
<<<<<<< HEAD
	String avaImg;
=======
>>>>>>> 9d5f547eac41afd5fc05cf6692cb71a3c3d2d19b
	Boolean trangThai;

	@ManyToOne
	@JoinColumn(name = "idLoaiKH")
	private LoaiKhachHang loaiKhachHang;
<<<<<<< HEAD
=======

	@Override
	public String toString() {
		return "KhachHang [id=" + id + ", hoTen=" + hoTen + ", ngaySinh=" + ngaySinh + ", std=" + std + ", gioiTinh="
				+ gioiTinh + ", email=" + email + ", matKhau=" + matKhau + ", trangThai=" + trangThai
				+ ", loaiKhachHang=" + loaiKhachHang + "]";
	}

>>>>>>> 9d5f547eac41afd5fc05cf6692cb71a3c3d2d19b
}