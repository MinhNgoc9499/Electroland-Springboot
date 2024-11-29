package com.fpl.Electroland.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HinhSp {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	// Định nghĩa cột là varchar(225)
	@Column(columnDefinition = "varchar(225)", nullable = false)
	String link;

	// Mối quan hệ với bảng sản phẩm
	@ManyToOne
	@JoinColumn(name = "idSP", nullable = false)
	SanPham sanPham;
}
