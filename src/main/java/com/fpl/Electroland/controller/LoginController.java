package com.fpl.Electroland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fpl.Electroland.model.KhachHang;

@Controller
public class LoginController {

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
    	KhachHang kh = new KhachHang();
    	kh.setHoTen("Trần Ngọc Anh");
        model.addAttribute("user", kh); // Đối tượng mẫu
        return "login"; // Tên file Thymeleaf là register.html
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("khachHang") KhachHang khachHang) {
        // Xử lý đăng ký
        return "login"; // Trang thành công
    }
}
