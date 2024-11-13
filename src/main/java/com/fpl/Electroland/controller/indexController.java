package com.fpl.Electroland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.LoaiSanPhamDAO;
import com.fpl.Electroland.dao.QuyenDAO;
import com.fpl.Electroland.model.LoaiKhachHang;
import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.Quyen;


@Controller
public class indexController {
	
	@Autowired
	LoaiKhachHangDAO dao;
//	
	@GetMapping("/index")
	public String getIndexPage(Model model) {
		dao.save(new LoaiKhachHang(1,"vip1"));
		return "index";
	}
	
}
