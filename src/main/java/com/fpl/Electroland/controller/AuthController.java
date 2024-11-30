package com.fpl.Electroland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpl.Electroland.dao.NhanVienDAO;
import com.fpl.Electroland.dao.NhanVienDetailService;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.NhanVien;
import com.fpl.Electroland.model.User;


@ControllerAdvice
public class AuthController {

    @Autowired
    Author author;

    @Autowired
    NhanVienDetailService nhanVien;

    @Autowired
    NhanVienDAO nvDAO;

    @ModelAttribute("user")
    public KhachHang getUser() {
        return author.getUserKhachHang();
    }

    @ModelAttribute("admin")
    public NhanVien getAdmin(@AuthenticationPrincipal User user) {
        if (user != null) {
            return nhanVien.findByEmail(user.getEmail()).orElse(null); 
        }
        return null;
    }

}
