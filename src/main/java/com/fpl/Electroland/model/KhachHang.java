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
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
	@NotBlank(message = "Họ tên không được để trống")
	String hoTen;

	// Sử dụng @Temporal cho trường ngày sinh
	@Column(nullable = false)
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull(message = "Ngày sinh không được để trống")
	Date ngaySinh;

	// Định nghĩa cột sdt là varchar(15)
	@Column(columnDefinition = "varchar(15)", nullable = true)
	@NotBlank(message = "Số điện thoại không được để trống")
	@Pattern(regexp = "\\d{10,15}", message = "Số điện thoại không đúng định dạng")
	String sdt;

	Boolean gioiTinh;

	// Định nghĩa cột email và matKhau là varchar(225)
	@Column(columnDefinition = "varchar(225)", nullable = true)
	@NotBlank(message = "Email không được để trống")
	@Email(message = "Email không đúng định dạng")
	String email;

	// Định nghĩa cột matKhau là varchar(225)
	@Column(columnDefinition = "varchar(225)", nullable = false)
	@NotBlank(message = "Mật khẩu không được để trống")
	@Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
	@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", 
	         message = "Mật khẩu phải chứa ít nhất một chữ cái, một số và một ký tự đặc biệt")
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
