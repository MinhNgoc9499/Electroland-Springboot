package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class LogoutController {
	@Autowired
	private KhachHangDAO khDAO;

	@Autowired
	private Author author;

	@Autowired
	HttpServletRequest request;

	public List<KhachHang> getList() {
		List<KhachHang> list = new ArrayList<KhachHang>();
		list = khDAO.findAll();
		return list;
	}

	@ModelAttribute("khachHang")
	public KhachHang newKH() {
		return new KhachHang();
	}

	@ModelAttribute("khachHangDN")
	public KhachHang newKHDN(@CookieValue(name = "email", required = false) String email,
			@CookieValue(name = "password", required = false) String password) {
		KhachHang kh = new KhachHang();
		kh.setEmail(email);
		kh.setMatKhau(password);
		return kh;
	}

	public List<KhachHang> khList;

	@GetMapping("/dangxuat")
	public String dangxuat(Model model) {
		author.setUserKhachHang(null);
		return "redirect:/index";
	}
}
