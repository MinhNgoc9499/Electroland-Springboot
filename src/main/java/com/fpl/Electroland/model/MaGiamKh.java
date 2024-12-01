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
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaGiamKh {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Đảm bảo ngày hết hạn có kiểu Date và sử dụng @Temporal để xác định định dạng
	@Temporal(TemporalType.DATE) // Chỉ lưu ngày, không lưu thời gian
	@DateTimeFormat(pattern = "dd-MM-yyyy")
	@Column(nullable = true) // nullable = true
	Date ngayHetHan;

	// Đảm bảo trạng thái có kiểu Boolean và nullable = true
	@Column(nullable = true) // nullable = true
	Boolean trangThai;

	@ManyToOne
	@JoinColumn(name = "idKH", nullable = false) // nullable = false
	KhachHang khachHang;

	@ManyToOne
	@JoinColumn(name = "idMGDH", nullable = true) // nullable = true
	MaGiamDh maGiamDh;

	@ManyToOne
	@JoinColumn(name = "idMGSP", nullable = true) // nullable = true
	MaGiamSp maGiamSp;

	@Column(nullable = true) // nullable = true
	Boolean checked;

}
