package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fpl.Electroland.dao.LoaiSanPhamDAO;
import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.NhaCungCap;
import com.fpl.Electroland.model.SanPham;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestcController {

	@Autowired
	LoaiSanPhamDAO loaiSanPhamDAO;

	@ModelAttribute("nav_topPhone")
	public List<String> getNav_topPhone() {
		List<String> items = Arrays.asList("Iphone", "Samsung", "Xiaomi", "Oppo", "Asus");
		return items;
	}

	SanPham sanPhamMau = new SanPham(1, "iPhone 16 8GB 256GB | Chính hãng VN/A", "iphone-16-1.webp", "Mô tả",
			22490000.0,
			22990000.0, true,
			new LoaiSanPham(1, "Điện thoại", "Hình"), new NhaCungCap(1, "Appo", "Hình"));

	@ModelAttribute(name = "List6")
	public List<SanPham> getSPTest() {
		ArrayList<SanPham> list = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			list.add(sanPhamMau);
		}
		return list;
	}

	@ModelAttribute("listCategory")
	public List<LoaiSanPham> getListCategory() {
		return loaiSanPhamDAO.findAll();
	}

	@RequestMapping("/testlayout")
	public String test(Model model) {
		return "Layout_Index_Test";
	}

	@GetMapping("/userAdd")
	public String getMethodName() {
		return "user-address";
	}

}
