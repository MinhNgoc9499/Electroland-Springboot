package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;

@Controller
public class CheckoutController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	KhachHangDAO khDAO;

	@Autowired
	Author author;

	public List<KhachHang> getList() {
		List<KhachHang> list = new ArrayList<KhachHang>();
		list = khDAO.findAll();
		return list;
	}

	@GetMapping("/thanhtoan")
	public String showRegistrationForm(Model model) {
		return "checkout";
	}
}
