package com.fpl.Electroland.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoaiSanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    // Đảm bảo trường tenLoaiSP là nvarchar(225) và có thể null
    @Column(columnDefinition = "nvarchar(225)", nullable = false)
    String tenLoaiSP;

    // Đảm bảo trường hinh là varchar(225) và không thể null
    @Column(columnDefinition = "varchar(225)", nullable = false)
    String hinh;
}
