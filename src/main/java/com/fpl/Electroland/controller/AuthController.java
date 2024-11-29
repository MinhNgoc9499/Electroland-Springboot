package com.fpl.Electroland.controller;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpl.Electroland.dao.NhanVienDetailService;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.NhanVien;

import jakarta.servlet.http.HttpSession;

@ControllerAdvice
public class AuthController {

    @Autowired
    Author author;
    @Autowired
    NhanVienDetailService nhanVien;
    @ModelAttribute("user")
    public KhachHang getUser() {
        return author.getUserKhachHang();
    }
   
   
}
