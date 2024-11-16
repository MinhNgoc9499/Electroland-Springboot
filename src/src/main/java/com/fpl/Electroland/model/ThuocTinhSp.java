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
public class ThuocTinhSp {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		int id;
		String ten;
		
		@ManyToOne
		@JoinColumn(name = "idSP")
		SanPham idSP;
		
}
