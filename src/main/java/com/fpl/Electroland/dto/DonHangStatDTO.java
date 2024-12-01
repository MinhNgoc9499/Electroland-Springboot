package com.fpl.Electroland.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DonHangStatDTO {
    private int thang;
    private int donHangThanhCong;
    private int donHangThatBai;
    private double tongBan;
}
