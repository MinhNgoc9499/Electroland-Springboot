package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import com.fpl.Electroland.dao.GioHangDAO;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.MaGiamDhDAO;
import com.fpl.Electroland.dao.MaGiamKhDAO;
import com.fpl.Electroland.dao.MaGiamSpDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.MaGiamDh;
import com.fpl.Electroland.model.MaGiamKh;


@Controller
public class gioHangController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
    GioHangDAO gioHangDAO; // DAO để thao tác với giỏ hàng

	@Autowired
	Author author;
	
	@Autowired
	SanPhamDAO spDAO;

	@Autowired
	KhachHangDAO khDAO;

	@Autowired
	MaGiamDhDAO mgdhDao;

	@Autowired
	MaGiamKhDAO mgkhDao;

	@Autowired
	MaGiamSpDAO mgspDao;


	@ModelAttribute("List")
	public List<GioHang> getList() {
		List<GioHang> list = new ArrayList<>();
		list = gioHangDAO.findByKhachHang(author.getUserKhachHang());
		return list;
	}
	@ModelAttribute("Voucher")
	public List<MaGiamKh> getVoucher(){
		List<MaGiamKh> voucher = new ArrayList<>();
		voucher = mgkhDao.findByKhachHang(author.getUserKhachHang());
		return voucher;
	}

    @ModelAttribute("TotalMoney")
	public Double gettotal() {
		Double total = 0.0;
		for (GioHang gh : getList()) {
			total += gh.getSanPham().getGiaGiam() * gh.getSoLuong();
		}
		return total;
	}

	
	@ModelAttribute("Discount")
	public MaGiamDh getMaGiamDh() {
		return new MaGiamDh(1, 100000.0, 0.0, 100000.0, 500000.0, "Giảm 100.000 đơn trên 500.000");
	}
	// @ModelAttribute("Discout1")
	// public Double getDiscount(){
	// 	if(){

	// 	}
	// }


	@GetMapping("/giohang")
	public String getIndexPage(Model model) {
		return "GioHang";
	}

} 