package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpl.Electroland.dao.GioHangDAO;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.MaGiamDhDAO;
import com.fpl.Electroland.dao.MaGiamKhDAO;
import com.fpl.Electroland.dao.MaGiamSpDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.MaGiamDh;
import com.fpl.Electroland.model.NhaCungCap;
import com.fpl.Electroland.model.SanPham;

@Controller
public class gioHangController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	GioHangDAO gioHangDAO; // DAO để thao tác với giỏ hàng

	@Autowired
	Author author;

	@Autowired
	SanPhamDAO spDAO;

	@Autowired
	KhachHangDAO khDAO;

	@Autowired
	MaGiamDhDAO mgdhDao;

	@Autowired
	MaGiamKhDAO mgkhDao;

	@Autowired
	MaGiamSpDAO mgspDao;

	SanPham sanPhamMau1 = new SanPham(1, "iPhone 16 8GB 256GB | Chính hãng VN/A", "iphone-16-1.webp", "Mô tả",
			22490000.0,
			22990000.0, true,
			new LoaiSanPham(1, "Điện thoại", "Hình"), new NhaCungCap(1, "Appo", "Hình"));
	SanPham sanPhamMau2 = new SanPham(2, "iPhone 16 8GB 256GB | Chính hãng VN/A", "iphone-16-1.webp", "Mô tả",
			22490000.0,
			22990000.0, true,
			new LoaiSanPham(1, "Điện thoại", "Hình"), new NhaCungCap(1, "Appo", "Hình"));
	SanPham sanPhamMau3 = new SanPham(3, "iPhone 16 8GB 256GB | Chính hãng VN/A", "iphone-16-1.webp", "Mô tả",
			22490000.0,
			22990000.0, true,
			new LoaiSanPham(1, "Điện thoại", "Hình"), new NhaCungCap(1, "Appo", "Hình"));

	GioHang gioHangMau1 = new GioHang(1, 3, null, true, sanPhamMau1, new KhachHang());
	GioHang gioHangMau2 = new GioHang(2, 3, null, true, sanPhamMau2, new KhachHang());
	GioHang gioHangMau3 = new GioHang(3, 3, null, true, sanPhamMau2, new KhachHang());

	@ModelAttribute("ListSelected")
	public List<GioHang> getList() {
		List<GioHang> list = new ArrayList<>();
		list = gioHangDAO.findByKhachHang(author.getUserKhachHang());
		return list;
	}

	@ModelAttribute("TotalMoney")
	public Double gettotal() {
		Double total = 0.0;
		for (GioHang gh : getList()) {
			total += gh.getSanPham().getGiaGiam() * gh.getSoLuong();
		}
		return total;
	}

	@ModelAttribute("Discount")
	public MaGiamDh getMaGiamDh() {
		return new MaGiamDh(1, 100000.0, 0.0, 100000.0, 500000.0, "Giảm 100.000 đơn trên 500.000");
	}

	@PostMapping("/update-product-selection")
	@ResponseBody
	public String updateProductSelection(@RequestParam("sanPham") SanPham sanPham,
			@RequestParam("checked") boolean checked,
			@RequestParam("khachhang") KhachHang khachhang) {
		GioHang gioHang = gioHangDAO.findBySanPhamAndKhachHang(sanPham, khachhang); // Thay đổi phương thức DAO

		if (gioHang != null) {
			if (!checked) {
				// Nếu bỏ chọn (selected = false), xóa sản phẩm khỏi giỏ
				gioHangDAO.delete(gioHang);
				return "Sản phẩm đã được xóa khỏi giỏ hàng.";
			} else {
				// Nếu chọn lại (selected = true), chỉ cần cập nhật trạng thái checked
				gioHang.setChecked(true);
				gioHangDAO.save(gioHang); // Lưu lại thay đổi trạng thái
				return "Cập nhật thành công trạng thái chọn sản phẩm.";
			}
		}
		return "Sản phẩm không tìm thấy trong giỏ.";
	}

	@PostMapping("/update-all-products-selection")
	@ResponseBody
	public String updateAllProductSelection(@RequestParam("checked") boolean selected,
			@RequestParam("khachhang") KhachHang khachhang) {
		List<GioHang> gioHangs = gioHangDAO.findByKhachHang(khachhang); // Thay đổi phương thức DAO
		if (gioHangs != null && !gioHangs.isEmpty()) {
			if (!selected) {
				// Nếu bỏ chọn tất cả (selected = false), xóa tất cả sản phẩm khỏi giỏ
				gioHangDAO.deleteAll(gioHangs);
				return "Tất cả sản phẩm đã được xóa khỏi giỏ hàng.";
			} else {
				// Nếu chọn lại tất cả (selected = true), cập nhật trạng thái checked cho tất cả
				// sản phẩm
				for (GioHang gioHang : gioHangs) {
					gioHang.setChecked(true);
					gioHangDAO.save(gioHang);
				}
				return "Cập nhật thành công trạng thái chọn cho tất cả sản phẩm.";
			}
		}
		return "Giỏ hàng không có sản phẩm.";
	}

	@GetMapping("/giohang")
	public String getIndexPage(Model model) {
		return "GioHang";
	}

}