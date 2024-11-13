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
public class NhanVien {
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		int id;
		String hoTen,std,chucVu,email,matKhau;
		Boolean trangthai;
}
