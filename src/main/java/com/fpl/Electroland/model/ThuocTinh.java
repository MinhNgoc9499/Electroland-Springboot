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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThuocTinh {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	Double giaTri = 0.0;

	// Trường tên thuộc tính, nullable = false và columnDefinition là nvarchar(225)
	@Column(nullable = false, columnDefinition = "nvarchar(225)")
	String tenTT;

	// Quan hệ với thuộc tính sản phẩm, nullable = false (không thể không có thuộc
	// tính sản phẩm)
	@ManyToOne
	@JoinColumn(name = "idTTSP", nullable = false)
	ThuocTinhSp thuocTinhSp;
}
