package com.fpl.Electroland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;

@Controller
public class inforController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	Author author;

	@ModelAttribute("user")
	public KhachHang getUser() {
		return author.getUserKhachHang();
	}

	@GetMapping("/infor")
	public String getIndexPage(Model model) {
		return "myaccount";
	}

	@GetMapping("/order")
	public String getOrder(Model model) {
		return "order-history";
	}

}
