create database electroland
go 
use electroland
go 

-- Tạo bảng khách_hàng
create table KhachHang(
	id int Identity (1,1),
	hoTen nvarchar(255),
	ngaySinh date,
	sdt varchar(13),
	gioiTinh bit,
	email nvarchar(255),
	matKhau varchar(255),
	trangThai bit,
	idLoaiKH int,
	avaImg varchar(255),
	primary key (id)
)
go

-- Tạo bảng địa_chỉ (lưu địa chỉ khách hàng muốn giao hàng)
create table DiaChi(
	id int Identity (1,1),
	chiTiet nvarchar(255),
	toaDo varchar(255),
	khoangCach decimal,  --tính theo kilometer
	macDinh bit, -- Có phải địa chỉ mặc định không
	idKH int,
	primary key (id)
)
go

-- Tạo bảng loại_khách_hàng
create table LoaiKhachHang(
	id int Identity (1,1),
	tenLoai nvarchar(255),
	primary key (id)
)
go


-- Tạo bảng sản_phẩm
create table SanPham(
	id int Identity (1,1),
	tenSP nvarchar(255),
	hinhDaiDien varchar(255),
	moTa nvarchar(max),
	giaBan decimal,
	trangThai bit,
	idLoaiSP int,
	idNhaCC int,
	primary key (id)
)
go

-- Tạo bảng loại_sản_phẩm
create table LoaiSanPham(
	id int Identity (1,1),
	tenLoaiSP nvarchar(255),
	primary key (id)
)
go

-- Tạo bảng nhà_cung_cấp
create table NhaCungCap(
	id int Identity (1,1),
	tenNhaCC nvarchar(255),
	logo varchar(255),
	primary key (id)
)
go

-- Tạo bảng hình_sản_phẩm
create table HinhSP(
	id int Identity (1,1),
	link varchar(255),
	idSP int,
	primary key (id)
)
go

-- Tạo bảng màu (Lưu màu cơ bản)
create table Mau(
	id int Identity (1,1),
	tenMau nvarchar(255),
	primary key (id)
)
go

-- Tạo bảng màu_sản_phẩm (Lưu màu của từng sản phẩm, sp có nhiều màu)
create table MauSP(
	id int Identity (1,1),
	idSP int,
	idMau int,
	primary key (id)
)
go

-- Tạo bảng thuộc_tính_sản_phẩm 
create table ThuocTinhSP(
	id int Identity (1,1),
	ten nvarchar(255),
	idSP int,
	primary key (id)
)
go

-- Tạo bảng thuộc_tính
create table ThuocTinh(
	id int Identity (1,1),
	tenTT nvarchar(255),
	idTTSP int,
	primary key (id)
)
go

-- Tạo bảng giỏ_hàng
create table GioHang(
	id int Identity (1,1),
	soLuong int,
	idSP int,
	idKH int,
	primary key (id)
)
go

-- Tạo bảng đơn_hàng (hóa đơn)
create table DonHang(
	id int Identity (1,1),
	diaChi nvarchar(255),
	nguoiNhan nvarchar(255),
	sdt varchar(13),
	phuongThucTT nvarchar(255),
	ngayDH date,
	ngayGH date,
	trangThai bit,
	idKH int,
	idMGKH int,
	primary key (id)
)
go

-- Tạo bảng chi_tiết_đơn_hàng
create table ChiTietDH(
	id int Identity (1,1),
	soLuong int,
	giaBan decimal,
	idSP int,
	idDH int,
	primary key (id)
)
go

-- Tạo bảng mã_giảm_sản_phẩm
create table MaGiamSP(
	id int Identity (1,1),
	giaTri decimal,
	idSP int,
	primary key (id)
)
go

-- Tạo bảng mã_giảm_đơn_hàng
create table MaGiamDH(
	id int Identity (1,1),
	giamGiaVND decimal,
	phanTramGG decimal,
	maxGG decimal,
	minDonGia decimal,
	moTa nvarchar(max),
	primary key (id)
)
go

-- Tạo bảng mã_giảm_khách_hàng
create table MaGiamKH(
	id int Identity (1,1),
	loaiKM nvarchar(255),
	ngayHetHan date,
	trangThai bit,
	idKH int,
	idMGDH int,
	idMGSP int,
	primary key (id)
)
go

-- Tạo bảng nhân_viên (Admin)
create table NhanVien(
	id int Identity (1,1),
	hoTen nvarchar(255),
	std varchar(13),
	chucVu nvarchar(255),
	email nvarchar(255),
	matKhau varchar(255),
	trangThai bit,
	primary key (id)
)
go

-- Tạo bảng phân_quyền 
create table PhanQuyen (
	id int Identity (1,1),
	idNV int,
	idQuyen int,
	primary key (id)
)
go

-- Tạo bảng quyền (quyền cước Vịnh xuần, quyền công dân hoặc không)
create table Quyen (
	id int Identity (1,1),
	ten nvarchar(255),
	moTa nvarchar(max),
	primary key (id)
)
go

-- Tạo bảng bình_luận
create table BinhLuan (
	id int Identity (1,1),
	noiDung nvarchar(max),
	ngayTao date,
	idKH int,
	idSP int,
	primary key (id)
)
go

-- Tạo bảng đánh_giá
create table DanhGia (
	id int Identity (1,1),
	diem int check(diem >=0 and diem <=5),
	ngayTao date,
	idKH int,
	idSP int,
	primary key (id)
)
go
