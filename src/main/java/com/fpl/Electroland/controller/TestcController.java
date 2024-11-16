package com.fpl.Electroland.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fpl.Electroland.dao.LoaiSanPhamDAO;
import com.fpl.Electroland.model.LoaiSanPham;
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

	@ModelAttribute("listCategory")
	public List<LoaiSanPham> getListCategory() {
		return loaiSanPhamDAO.findAll();
	}

	@RequestMapping("/testlayout")
	public String test(Model model) {
		return "layout/test";
	}

	@GetMapping("/userAdd")
	public String getMethodName() {
		return "user-address";
	}

}
