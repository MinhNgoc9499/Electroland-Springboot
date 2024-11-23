package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.Date;
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
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class LoginController {
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

	@GetMapping("/dangky")
	public String showRegistrationForm(Model model) {
		return "login";
	}

	@PostMapping("/dangky")
	public String dangKy(@Valid @ModelAttribute("khachHang") KhachHang khachHangDK, BindingResult result, Model model,
			@RequestParam("ngaySinh") String ns) {
		if (isEmailExists(khachHangDK.getEmail())) {
			model.addAttribute("error", "Email đã được đăng ký");
			return "login";
		} else if (isSoDienThoaiExists(khachHangDK.getSdt())) {
			model.addAttribute("error", "Số điện thoại đã được đăng ký");
			return "login";
		} else {
			author.setUserKhachHang(khachHangDK);
			khDAO.save(khachHangDK);
			return "redirect:/index";
		}
	}

	@PostMapping("/dangnhap")
	public String dangNhao(@Valid @ModelAttribute("khachHangDN") KhachHang khachHangDN, BindingResult result,
			Model model,
			@RequestParam(value = "ghiNhoDN", required = false) String rememberMe, HttpServletResponse rep) {
		Optional<KhachHang> kiemTraDangNhap = khDAO.findByEmailAndMatKhau(khachHangDN.getEmail(),
				khachHangDN.getMatKhau());
		if (kiemTraDangNhap.isPresent()) {
			author.setUserKhachHang(kiemTraDangNhap.get());
			System.out.println(kiemTraDangNhap.toString());
			if (rememberMe != null) {
				Cookie emailCookie = new Cookie("email", khachHangDN.getEmail());
				Cookie passwordCookie = new Cookie("password", khachHangDN.getMatKhau());
				emailCookie.setMaxAge(7 * 24 * 60 * 60);
				passwordCookie.setMaxAge(7 * 24 * 60 * 60);
				rep.addCookie(emailCookie);
				rep.addCookie(passwordCookie);
			} else {
				Cookie emailCookie = new Cookie("email", null);
				Cookie passwordCookie = new Cookie("password", null);
				emailCookie.setMaxAge(0);
				passwordCookie.setMaxAge(0);
				rep.addCookie(emailCookie);
				rep.addCookie(passwordCookie);
			}
			return "redirect:/index";
		}
		model.addAttribute("errorDN", "Email hoặc mật khẩu không đúng");
		return "login";
	}

	public boolean isSoDienThoaiExists(String soDienThoai) {
		Optional<KhachHang> khachHang = getList().stream().filter(kh -> kh.getSdt().equals(soDienThoai)).findFirst();
		return khachHang.isPresent();
	}

	public boolean isEmailExists(String email) {
		return khDAO.findByEmail(email).isPresent();
	}

}
