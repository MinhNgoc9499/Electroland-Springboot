package com.fpl.Electroland.model;



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
public class SanPham {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	String tenSP,hinhDaiDien,moTa;
	Double giaBan,giaGiam;
	Boolean trangThai;
	
	@ManyToOne
	@JoinColumn(name = "idLoaiSP")
	LoaiSanPham loaiSanPham;
	
	@ManyToOne
	@JoinColumn(name = "idNhaCC")
	NhaCungCap nhaCungCap;
	
}
