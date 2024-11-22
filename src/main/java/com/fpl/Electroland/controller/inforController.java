package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;

@Controller
public class inforController {

	@Autowired
	LoaiKhachHangDAO dao;
<<<<<<< HEAD
	
	@Autowired
	KhachHangDAO khDAO;
	
	@Autowired
	Author author;
	
=======

	@Autowired
	KhachHangDAO khDAO;

	@Autowired
	Author author;

>>>>>>> 6441d502985b778ca713d515861a617c3765003a
	public List<KhachHang> getList() {
		List<KhachHang> list = new ArrayList<KhachHang>();
		list = khDAO.findAll();
		return list;
	}
<<<<<<< HEAD
	
	
	
	@ModelAttribute("user")
	public KhachHang getUser() {
		return author.getUserKhachHang();
	}
=======
>>>>>>> 6441d502985b778ca713d515861a617c3765003a

	@GetMapping("/infor")
	public String getIndexPage(Model model) {
		return "_user_infor";
	}

	@GetMapping("/order")
	public String getOrder(Model model) {
		return "_user_order_history";
	}

	@GetMapping("/addre")
	public String getAddress(Model model) {
		return "_user_address";
	}

	@GetMapping("/oder_detail")
	public String getOderDetail(Model model) {
		return "_user_order_history_detail";
	}
<<<<<<< HEAD
	
	
	@PostMapping("/infor")
	public String updateInfor(@ModelAttribute("user") KhachHang user, BindingResult result, Model model) {
		Optional<KhachHang> userLogin = khDAO.findById(author.getUserKhachHang().getId());
		if(!user.getSdt().equals(userLogin.get().getSdt()) && isSoDienThoaiExists(user.getSdt())) {
			model.addAttribute("error", "Số điện thoại không hợp lệ");
			return "_user_infor";
		}else if(!user.getEmail().equals(userLogin.get().getEmail()) && isEmailExists(user.getEmail())) {
			model.addAttribute("error", "Email không hợp lệ");
			return "_user_infor";
		}else {
			khDAO.save(user);
		}
		
		return "_user_infor";
	}
	
=======

	@PostMapping("/infor")
	public String updateInfor(@ModelAttribute("user") KhachHang user, BindingResult result, Model model) {
		Optional<KhachHang> userLogin = khDAO.findById(author.getUserKhachHang().getId());
		if (!user.getSdt().equals(userLogin.get().getSdt()) && isSoDienThoaiExists(user.getSdt())) {
			model.addAttribute("error", "Số điện thoại không hợp lệ");
			return "_user_infor";
		} else if (!user.getEmail().equals(userLogin.get().getEmail()) && isEmailExists(user.getEmail())) {
			model.addAttribute("error", "Email không hợp lệ");
			return "_user_infor";
		} else {
			khDAO.save(user);
		}

		return "_user_infor";
	}

>>>>>>> 6441d502985b778ca713d515861a617c3765003a
	public boolean isSoDienThoaiExists(String soDienThoai) {
		Optional<KhachHang> khachHang = getList().stream().filter(kh -> kh.getSdt().equals(soDienThoai)).findFirst();
		return khachHang.isPresent();
	}

	public boolean isEmailExists(String email) {
		return khDAO.findByEmail(email).isPresent();
	}
}
