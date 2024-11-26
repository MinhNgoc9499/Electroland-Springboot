package com.fpl.Electroland.model;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
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
	private int id; // Primary Key

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)") // Sử dụng NVARCHAR(225)
	private String diaChi;

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)") // Sử dụng NVARCHAR(225)
	private String nguoiNhan;

	@Column(nullable = false, columnDefinition = "VARCHAR(15)") // Sử dụng VARCHAR(15)
	private String sdt;

	@Column(nullable = false, columnDefinition = "NVARCHAR(225)") // Sử dụng NVARCHAR(225)
	private String phuongThucTT;

	@Temporal(TemporalType.TIMESTAMP) // Lưu cả ngày và giờ
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") // Định dạng ngày tháng năm giờ:phút
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm") // Định dạng ngày tháng năm giờ:phút (dành cho JSON)
	@Column(nullable = false)
	private Date ngayDH = new Date(); // Ngày đơn hàng

	@Temporal(TemporalType.TIMESTAMP) // Lưu cả ngày và giờ
	@DateTimeFormat(pattern = "dd-MM-yyyy HH:mm") // Định dạng ngày tháng năm giờ:phút
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm") // Định dạng ngày tháng năm giờ:phút (dành cho JSON)
	@Column(nullable = true)
	private Date ngayGH; // Ngày giao hàng

	@Column(nullable = false) // Trạng thái không được null
	private int trangThai;

	@ManyToOne
	@JoinColumn(name = "idKH", nullable = false) // Khóa ngoại với KhachHang
	private KhachHang khachHang;

	@ManyToOne
	@JoinColumn(name = "idMGKH", nullable = true) // Khóa ngoại với MaGiamKh, có thể null
	private MaGiamKh maGiamKh;
	@OneToMany(mappedBy = "donHang", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ChiTietDh> chiTietDhs;

}
