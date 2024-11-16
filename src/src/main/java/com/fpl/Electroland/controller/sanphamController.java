package com.fpl.Electroland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;

@Controller
public class sanphamController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	Author author;

	@Scope("session")
	@ModelAttribute("user")
	public KhachHang getUser() {
		return author.getUserKhachHang();
	}

	@GetMapping("/sanpham")
	public String getIndexPage(Model model) {
		return "SanPham";
	}

}
