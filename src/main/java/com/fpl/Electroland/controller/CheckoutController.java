package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fpl.Electroland.dao.ChiTietDhDAO;
import com.fpl.Electroland.dao.DonHangDAO;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.DonHang;
import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.NhaCungCap;
import com.fpl.Electroland.model.SanPham;

import jakarta.validation.Valid;

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
			10000.0,
			10000.0, true,
			new LoaiSanPham(1, "Điện thoại", "Hình"), new NhaCungCap(1, "Appo", "Hình"));
	SanPham sanPhamMau2 = new SanPham(2, "iPhone 16 8GB 256GB | Chính hãng VN/A", "iphone-16-1.webp", "Mô tả",
			1000.0,
			1000.0, true,
			new LoaiSanPham(1, "Điện thoại", "Hình"), new NhaCungCap(1, "Appo", "Hình"));
	SanPham sanPhamMau3 = new SanPham(3, "iPhone 16 8GB 256GB | Chính hãng VN/A", "iphone-16-1.webp", "Mô tả",
			1000.0,
			1000.0, true,
			new LoaiSanPham(1, "Điện thoại", "Hình"), new NhaCungCap(1, "Appo", "Hình"));
			GioHang gioHangMau1 = new GioHang(1, 1, sanPhamMau1, null, new KhachHang(),true);
			GioHang gioHangMau2 = new GioHang(2, 3, sanPhamMau2, null, new KhachHang(),true);
			GioHang gioHangMau3 = new GioHang(3, 2, sanPhamMau3, null, new KhachHang(),true);

	// @ModelAttribute("ListSelected")
	// public List<SanPham> getList(@RequestParam("id") Integer[] listsp) {
	// ArrayList<SanPham> list = new ArrayList<>();
	// for (Integer id : listsp) {
	// list.add(spDAO.findById(id).get());
	// }
	// return list;
	// }

	@ModelAttribute("donHang")
	public DonHang getDonHang() {
		return new DonHang();
	};

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

	@ModelAttribute("TotalDiscount")
	public Double getMaGiamDh() {
		return 10000.0;
	}

	@GetMapping("/thanhtoan")
	public String showRegistrationForm(Model model) {
		UUID uuid = UUID.randomUUID();
		model.addAttribute("key", uuid.toString().replaceAll("-", ""));
		return "checkout";
	}

	@PostMapping("/thanhtoan")
	public String Payment(@Valid @ModelAttribute("DonHang") DonHang donhang, BindingResult rs, Model model) {
		return "redirect:/order_detail?id=" + donhang.getId();
	}
}
