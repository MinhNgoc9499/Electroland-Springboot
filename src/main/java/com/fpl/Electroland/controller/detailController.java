package com.fpl.Electroland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.helper.Author;

@Controller
public class detailController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	Author author;

	@GetMapping("/detail")
	public String getIndexPage(Model model) {
		return "Detail";
	}

}