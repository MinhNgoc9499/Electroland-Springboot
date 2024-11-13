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
public class KhachHang {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String hoTen;
	Date ngaySinh;
	String std;
	Boolean gioiTinh;
	String email,matKhau;
	Boolean trangThai;
	
	@ManyToOne 
    @JoinColumn(name = "idLoaiKH") 
    private LoaiKhachHang loaiKhachHang;
}
