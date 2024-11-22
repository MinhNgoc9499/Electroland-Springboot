package com.fpl.Electroland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;

@Controller
public class gioHangController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	Author author;

	@ModelAttribute("user")
	public KhachHang getUser() {
		return author.getUserKhachHang();
	}

	@GetMapping("/giohang")
	public String getIndexPage(Model model) {
		return "GioHang";
	}

}
