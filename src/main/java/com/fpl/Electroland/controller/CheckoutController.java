package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpl.Electroland.dao.ChiTietDhDAO;
import com.fpl.Electroland.dao.DonHangDAO;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.MaGiamDh;
import com.fpl.Electroland.model.NhaCungCap;
import com.fpl.Electroland.model.SanPham;

@Controller
public class CheckoutController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	KhachHangDAO khDAO;

	@Autowired
	Author author;

	@Autowired
	SanPhamDAO spDAO;

	@Autowired
	DonHangDAO dhDAO;

	@Autowired
	ChiTietDhDAO ctdhDAO;

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

	GioHang gioHangMau1 = new GioHang(1, 1, sanPhamMau1, new KhachHang());
	GioHang gioHangMau2 = new GioHang(2, 3, sanPhamMau2, new KhachHang());
	GioHang gioHangMau3 = new GioHang(3, 2, sanPhamMau3, new KhachHang());

	// @ModelAttribute("ListSelected")
	// public List<SanPham> getList(@RequestParam("id") Integer[] listsp) {
	// ArrayList<SanPham> list = new ArrayList<>();
	// for (Integer id : listsp) {
	// list.add(spDAO.findById(id).get());
	// }
	// return list;
	// }

	@ModelAttribute("ListSelected")
	public List<GioHang> getList() {
		ArrayList<GioHang> list = new ArrayList<>();
		list.add(gioHangMau1);
		list.add(gioHangMau2);
		list.add(gioHangMau3);
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

	@GetMapping("/thanhtoan")
	public String showRegistrationForm(Model model) {
		return "checkout";
	}
}
