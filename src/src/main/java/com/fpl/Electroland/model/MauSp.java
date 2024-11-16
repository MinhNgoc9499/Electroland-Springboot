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
public class MauSp {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		int id;
		
		@ManyToOne
		@JoinColumn(name = "idSP")
		SanPham sanPham;
		
		@ManyToOne
		@JoinColumn(name = "idMau")
		Mau mau;
}
