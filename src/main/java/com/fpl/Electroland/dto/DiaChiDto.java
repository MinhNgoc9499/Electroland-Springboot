package com.fpl.Electroland.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaChiDto {

	private int id;

	private String chiTiet;

	private boolean macDinh;

	private String hoTen;

	private String sdt;
	
    private String loaiDiaChi;
}
