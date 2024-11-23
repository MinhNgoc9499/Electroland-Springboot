package com.fpl.Electroland.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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

	// Định nghĩa cột hoTen là nvarchar(225)
	@Column(columnDefinition = "nvarchar(225)", nullable = false)
	String hoTen;

	// Sử dụng @Temporal cho trường ngày sinh
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date ngaySinh;

	// Định nghĩa cột sdt là varchar(15)
	@Column(columnDefinition = "varchar(15)", nullable = true)
	String sdt;

	Boolean gioiTinh;

	// Định nghĩa cột email và matKhau là varchar(225)
	@Column(columnDefinition = "varchar(225)", nullable = true)
	String email;

	// Định nghĩa cột matKhau là varchar(225)
	@Column(columnDefinition = "varchar(225)", nullable = false)
	String matKhau;

	// Định nghĩa cột avaImg là varchar(225)
	@Column(columnDefinition = "varchar(225)", nullable = true)
	String avaImg;

	@Column(nullable = true)
	Boolean trangThai;

	// Mối quan hệ với bảng LoaiKhachHang
	@ManyToOne
	@JoinColumn(name = "idLoaiKH", nullable = true)
	private LoaiKhachHang loaiKhachHang;
}
