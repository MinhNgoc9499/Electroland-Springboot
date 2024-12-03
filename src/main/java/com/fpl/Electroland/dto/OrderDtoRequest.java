package com.fpl.Electroland.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDtoRequest {

    private int id;
    private String hoTen;
    private String sdt;
    private String diaChi;
    private String phuongThucTT;
    private String ngayDH;
    private String ngayGH;
    private int trangThai;
    private List<ProductDTO> chiTietDhs;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class ProductDTO {
        private int id;
        private int soLuong;
    }

}
