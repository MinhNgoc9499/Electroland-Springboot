package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.LoaiSanPhamDAO;
import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.NhaCungCap;
import com.fpl.Electroland.model.SanPham;

@Controller
public class indexController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	LoaiSanPhamDAO loaiSanPhamDAO;

	@ModelAttribute("nav_topPhone")
	public List<String> getNav_topPhone() {
		List<String> items = Arrays.asList("IPHONE", "SAMSUNG", "XIAOMI", "OPPO", "ASUS", "Xem tất cả");
		return items;
	}

	@ModelAttribute("nav_topLaptop")
	public List<String> getNav_topLaptop() {
		List<String> items = Arrays.asList("MAC", "HP", "DELL", "ASUS", "ACER", "Xem tất cả");
		return items;
	}

	SanPham sanPhamMau = new SanPham(1, "iPhone 16 8GB 256GB | Chính hãng VN/A", "iphone-16-1.webp", "Mô tả",
			22490000.0,
			22990000.0, true,
			new LoaiSanPham(1, "Điện thoại", "Hình"), new NhaCungCap(1, "Appo", "Hình"));

	LoaiSanPham loaiSanPhamMau = new LoaiSanPham(1, "Điện thoại", "caseiphone.jpg");

	@ModelAttribute(name = "List6")
	public List<SanPham> getSPTest() {
		ArrayList<SanPham> list = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			list.add(sanPhamMau);
		}
		return list;
	}

	@ModelAttribute(name = "List12")
	public List<SanPham> getSPTest18() {
		ArrayList<SanPham> list = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			list.add(sanPhamMau);
		}
		return list;
	}

	@ModelAttribute(name = "List20")
	public List<SanPham> getSPTest20() {
		ArrayList<SanPham> list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			list.add(sanPhamMau);
		}
		return list;
	}

	@ModelAttribute(name = "listCategory")
	public List<LoaiSanPham> getListCategori() {
		ArrayList<LoaiSanPham> list = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			list.add(loaiSanPhamMau);
		}
		return list;
	}

	@GetMapping("/index")
	public String getIndexPage(Model model) {
		return "Index_Page";
	}

}
