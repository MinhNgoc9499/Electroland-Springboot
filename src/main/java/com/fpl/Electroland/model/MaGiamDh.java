package com.fpl.Electroland.model;

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
public class MaGiamDh {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		int id;
		Double giamGiaVND,phanTramGG,maxGG,minDonGia;
		String moTa;
}
