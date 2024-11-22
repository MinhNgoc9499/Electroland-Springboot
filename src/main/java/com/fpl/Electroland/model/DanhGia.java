package com.fpl.Electroland.model;

import java.util.Date;
import jakarta.persistence.*;
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
	private int id; // Primary Key

	@Column(nullable = false) // Đảm bảo cột này không null
	private int diem;

	@Temporal(TemporalType.TIMESTAMP) // Sử dụng TIMESTAMP để lưu cả ngày và giờ
	@Column(nullable = false) // Đảm bảo cột này không null
	private Date ngayTao = new Date(); // Ngày tạo với giá trị mặc định là ngày giờ hiện tại

	@ManyToOne
	@JoinColumn(name = "idKH", nullable = false) // Khóa ngoại liên kết với KhachHang
	private KhachHang khachHang;

	@ManyToOne
	@JoinColumn(name = "idSP", nullable = false) // Khóa ngoại liên kết với SanPham
	private SanPham sanPham;
}
