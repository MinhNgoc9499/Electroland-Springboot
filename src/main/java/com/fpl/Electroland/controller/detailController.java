package com.fpl.Electroland.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpl.Electroland.dao.HinhSpDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.MauSpDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.dao.ThuocTinhDAO;
import com.fpl.Electroland.dao.ThuocTinhSpDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.HinhSp;
import com.fpl.Electroland.model.MauSp;
import com.fpl.Electroland.model.SanPham;
import com.fpl.Electroland.model.ThuocTinh;

@Controller
public class detailController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	SanPhamDAO spDAO;

	@Autowired
	HinhSpDAO hinhDAO;

	@Autowired
	MauSpDAO mauDAO;

	@Autowired
	ThuocTinhDAO ttDAO;

	@Autowired
	ThuocTinhSpDAO ttSPDAO;

	
	@Autowired
	Author author;


	
	
	@GetMapping("/detail")
	public String getIndexPage(@RequestParam("id") int id, Model model) {
		Optional<SanPham> sp = spDAO.findById(id);
		if(sp.isPresent()){
			List<HinhSp> listHinh = hinhDAO.findBySanPham(sp.get());
			List<MauSp> listColor = mauDAO.findBySanPham(sp.get());
			model.addAttribute("product",sp.get());
			model.addAttribute("pictures", listHinh);
			model.addAttribute("colors", listColor);
		}
		return "Detail";
	}

}
