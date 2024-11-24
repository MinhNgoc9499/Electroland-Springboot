INSERT INTO [dbo].[KhachHang] ( [avaImg], [email], [gioiTinh], [hoTen], [matKhau], [ngaySinh], [sdt], [trangThai], [idLoaiKH]) 
SELECT N'avatar.jpg', N'ankankbk54@gmail.com', 0, N'Trần Ngọc Anh', N'1234', CAST(N'2024-01-14' AS Date), N'0865854002', 1, NULL
WHERE NOT EXISTS (SELECT 1 FROM KhachHang WHERE email = N'ankankbk54@gmail.com');
