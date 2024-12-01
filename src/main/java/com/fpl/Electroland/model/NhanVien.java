package com.fpl.Electroland.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NhanVien {

	// Khóa chính với giá trị tự động tăng
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Họ và tên của nhân viên, không thể là NULL và sử dụng NVARCHAR(225)
	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String hoTen;

	// Số điện thoại của nhân viên, không thể là NULL và sử dụng NVARCHAR(15)
	@Column(nullable = false, columnDefinition = "varchar(15)")
	String std;

	// Chức vụ của nhân viên, có thể là NULL và sử dụng NVARCHAR(225)
	@Column(nullable = true, columnDefinition = "nvarchar(225)")
	String chucVu;

	// Email của nhân viên, không thể là NULL và sử dụng VARCHAR(225)
	@Column(nullable = false, columnDefinition = "varchar(225)")
	String email;

	// Mật khẩu của nhân viên, không thể là NULL và sử dụng VARCHAR(225)
	@Column(nullable = false, columnDefinition = "varchar(225)")
	String matKhau;

	// Trạng thái của nhân viên, có thể là NULL
	@Column(nullable = true)
	Boolean trangthai;


}
