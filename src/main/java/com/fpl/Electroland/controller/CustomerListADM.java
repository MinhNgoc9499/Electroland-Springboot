package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.fpl.Electroland.dao.GioHangDAO;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.KhachHang;

@Controller
public class CustomerListADM {

    @Autowired
    KhachHangDAO khDao;
    @Autowired
	Author author;

    @ModelAttribute("List")
	public List<KhachHang> getList() {
		List<KhachHang> list = new ArrayList<>();
		return list;
	}


    @GetMapping("/adminCustomerList")
	public String adminCustomerList() {
		return "CustomerList";
	}
    
}
