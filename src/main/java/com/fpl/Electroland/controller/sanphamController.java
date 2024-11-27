package com.fpl.Electroland.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.SanPham;

@Controller
public class sanphamController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	Author author;

	@Autowired
	SanPhamDAO sanPhamDAO;
	
	@GetMapping("/sanpham")
	public String getIndexPage(Model model) {
		List<SanPham> dsSP = sanPhamDAO.findAll();
		model.addAttribute("product", dsSP);
		return "SanPham";
	}
}
