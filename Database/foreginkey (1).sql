use electroland
go

-- Tạo ràng buộc cho bảng địa_chỉ
ALTER TABLE DiaChi
ADD CONSTRAINT fk_diachi_khachhang
FOREIGN KEY (idKH) REFERENCES KhachHang(id)
go

-- Tạo ràng buộc cho bảng khách_hàng
ALTER TABLE KhachHang
ADD CONSTRAINT fk_khachhang_loaikhachhang
FOREIGN KEY (idLoaiKH) REFERENCES LoaiKhachHang(id)
go

-- Tạo ràng buộc cho bảng sản_phẩm
ALTER TABLE SanPham
ADD CONSTRAINT fk_sanPham_loaiSP
FOREIGN KEY (idLoaiSP) REFERENCES LoaiSanPham(id)
go

ALTER TABLE SanPham
ADD CONSTRAINT fk_sanPham_nhaCC
FOREIGN KEY (idNhaCC) REFERENCES NhaCungCap(id)
go

ALTER TABLE SanPham
ADD CONSTRAINT uc_idNhaCC_idLoaiSP UNIQUE (idNhaCC, idLoaiSP);
go

-- Tạo ràng buộc cho bảng hình_sản_phẩm
ALTER TABLE HinhSP
ADD CONSTRAINT fk_hinhSP_sanPham
FOREIGN KEY (idSP) REFERENCES SanPham(id)
go

-- Tạo ràng buộc cho bảng màu_sản_phẩm
ALTER TABLE MauSP
ADD CONSTRAINT fk_mauSP_sanPham
FOREIGN KEY (idSP) REFERENCES SanPham(id)
go

ALTER TABLE MauSP
ADD CONSTRAINT fk_mauSP_mau
FOREIGN KEY (idMau) REFERENCES Mau(id)
go

ALTER TABLE MauSP
ADD CONSTRAINT uc_idMau_idSP UNIQUE (idSP, idMau);
go

-- Tạo ràng buộc cho bảng thuộc_tính_sản_phẩm
ALTER TABLE ThuocTinhSP
ADD CONSTRAINT fk_thuocTinhSP_sanPham
FOREIGN KEY (idSP) REFERENCES SanPham(id)
go

-- Tạo ràng buộc cho bảng thuộc_tính
ALTER TABLE ThuocTinh
ADD CONSTRAINT fk_thuocTinh_thuocTinhSP
FOREIGN KEY (idTTSP) REFERENCES ThuocTinhSP(id)
go

-- Tạo ràng buộc cho bảng giỏ_hàng
ALTER TABLE GioHang
ADD CONSTRAINT fk_gioHang_sanPham
FOREIGN KEY (idSP) REFERENCES SanPham(id)
go

ALTER TABLE GioHang
ADD CONSTRAINT fk_gioHang_khachHang
FOREIGN KEY (idKH) REFERENCES KhachHang(id)
go

ALTER TABLE GioHang
ADD CONSTRAINT uc_idSP_idKH UNIQUE (idSP, idKH);
go

-- Tạo ràng buộc cho bảng đơn_hàng
ALTER TABLE DonHang
ADD CONSTRAINT fk_donHang_khachHang
FOREIGN KEY (idKH) REFERENCES KhachHang(id)
go

ALTER TABLE DonHang
ADD CONSTRAINT fk_donHang_maGiamKH
FOREIGN KEY (idMGKH) REFERENCES MaGiamKH(id)
go

ALTER TABLE DonHang
ADD CONSTRAINT uc_idKH_idMGKH UNIQUE (idKH, idMGKH);
go

-- Tạo ràng buộc cho bảng chi_tiết_đơn_hàng
ALTER TABLE ChiTietDH
ADD CONSTRAINT fk_chiTietDH_donHang
FOREIGN KEY (idDH) REFERENCES DonHang(id)
go

ALTER TABLE ChiTietDH
ADD CONSTRAINT fk_chiTietDH_sanPham
FOREIGN KEY (idSP) REFERENCES SanPham(id)
go

ALTER TABLE ChiTietDH
ADD CONSTRAINT uc_idDH_idSP UNIQUE (idDH, idSP);
go

-- Tạo ràng buộc cho bảng mã_giảm_sản_phẩm
ALTER TABLE MaGiamSP
ADD CONSTRAINT fk_maGiamSP_sanPham
FOREIGN KEY (idSP) REFERENCES SanPham(id)
go

-- Tạo ràng buộc cho bảng mã_giảm_khách_hàng
ALTER TABLE MaGiamKH
ADD CONSTRAINT fk_maGiamKH_khachHang
FOREIGN KEY (idKH) REFERENCES KhachHang(id)
go

ALTER TABLE MaGiamKH
ADD CONSTRAINT fk_maGiamKH_maGiamSP
FOREIGN KEY (idMGSP) REFERENCES MaGiamSP(id)
go

ALTER TABLE MaGiamKH
ADD CONSTRAINT fk_maGiamKH_maGiamDH
FOREIGN KEY (idMGDH) REFERENCES MaGiamDH(id)
go

ALTER TABLE MaGiamKH
ADD CONSTRAINT uc_idKH_idMGSP UNIQUE (idKH, idMGSP);
go

ALTER TABLE MaGiamKH
ADD CONSTRAINT uc_idKH_idMGDH UNIQUE (idKH, idMGDH);
go

-- Tạo ràng buộc cho bảng phân_quyền
ALTER TABLE PhanQuyen
ADD CONSTRAINT fk_phanQuyen_nhanVien
FOREIGN KEY (idNV) REFERENCES NhanVien(id)
go

ALTER TABLE PhanQuyen
ADD CONSTRAINT fk_phanQuyen_quyen
FOREIGN KEY (idQuyen) REFERENCES Quyen(id)
go

ALTER TABLE PhanQuyen
ADD CONSTRAINT uc_idNV_idQuyen UNIQUE (idNV, idQuyen);
go

-- Tạo ràng buộc cho bảng bình_luận
ALTER TABLE BinhLuan
ADD CONSTRAINT fk_binhLuan_khachHang
FOREIGN KEY (idKH) REFERENCES KhachHang(id)
go

ALTER TABLE BinhLuan
ADD CONSTRAINT fk_binhLuan_sanPham
FOREIGN KEY (idSP) REFERENCES SanPham(id)
go

ALTER TABLE BinhLuan
ADD CONSTRAINT uc_binhluan_idKH_idSP UNIQUE (idKH, idSP);
go

-- Tạo ràng buộc cho bảng đánh_giá
ALTER TABLE DanhGia
ADD CONSTRAINT fk_danhGia_khachHang
FOREIGN KEY (idKH) REFERENCES KhachHang(id)
go

ALTER TABLE DanhGia
ADD CONSTRAINT fk_danhGia_sanPham
FOREIGN KEY (idSP) REFERENCES SanPham(id)
go

ALTER TABLE DanhGia
ADD CONSTRAINT uc_danggia_idKH_idSP UNIQUE (idKH, idSP);
go
