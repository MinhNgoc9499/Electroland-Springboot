package com.fpl.Electroland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;

@ControllerAdvice
public class AuthController {

    @Autowired
    Author author;

    @ModelAttribute("user")
    public KhachHang getUser() {
        return author.getUserKhachHang();
    }
}
