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
public class detailController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	Author author;

<<<<<<< HEAD
	@ModelAttribute("user")
	public KhachHang getUser() {
		return author.getUserKhachHang();
	}

=======
>>>>>>> 6441d502985b778ca713d515861a617c3765003a
	@GetMapping("/detail")
	public String getIndexPage(Model model) {
		return "Detail";
	}

}
