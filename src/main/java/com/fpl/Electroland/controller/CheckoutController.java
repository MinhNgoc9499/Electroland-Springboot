package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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

	@ModelAttribute("user")
	public KhachHang getUser() {
		return author.getUserKhachHang();
	}

	@GetMapping("/thanhtoan")
	public String showRegistrationForm(Model model) {
		return "checkout";
	}
}
