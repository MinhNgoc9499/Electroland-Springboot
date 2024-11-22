package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;
<<<<<<< HEAD
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

=======
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
>>>>>>> 6441d502985b778ca713d515861a617c3765003a
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;

<<<<<<< HEAD
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
=======
import jakarta.servlet.http.HttpServletRequest;
>>>>>>> 6441d502985b778ca713d515861a617c3765003a

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
<<<<<<< HEAD
		author.setUserKhachHang(new KhachHang());
=======
		author.setUserKhachHang(null);
>>>>>>> 6441d502985b778ca713d515861a617c3765003a
		return "redirect:/index";
	}
}
