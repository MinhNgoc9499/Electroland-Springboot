package com.fpl.Electroland.controller;

import com.fpl.Electroland.dao.CloudinaryService;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class inforController {

	private static final Logger log = LoggerFactory.getLogger(inforController.class);
	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	KhachHangDAO khDAO;

	@Autowired
	Author author;

	/**
	 * Service class to handle file uploads to Cloudinary.
	 */
	@Autowired
	private CloudinaryService cloudinaryService;

	public List<KhachHang> getList() {
		List<KhachHang> list = new ArrayList<KhachHang>();
		list = khDAO.findAll();
		return list;
	}

	@ModelAttribute("user")
	public KhachHang getUser() {
		return author.getUserKhachHang();
	}

	@GetMapping("/infor")
	public String getIndexPage(Model model) {
		return "_user_infor";
	}

	@GetMapping("/order_history")
	public String getOrder(Model model) {
		return "_user_order_history";
	}

	@GetMapping("/user_address")
	public String getAddress(Model model) {
		return "_user_address";
	}

	@GetMapping("/order_detail")
	public String getOderDetail(Model model) {
		return "_user_order_history_detail";
	}

	@PostMapping("/infor")
	public String updateInfor(@ModelAttribute("user") KhachHang user, Model model, @RequestParam("file") MultipartFile file)
        throws IOException {
		Optional<KhachHang> userLogin = khDAO.findById(author.getUserKhachHang().getId());
		if (!user.getSdt().equals(userLogin.get().getSdt()) && isSoDienThoaiExists(user.getSdt())) {
			model.addAttribute("error", "Số điện thoại không hợp lệ");
			return "_user_infor";
		} else if (!user.getEmail().equals(userLogin.get().getEmail()) && isEmailExists(user.getEmail())) {
			model.addAttribute("error", "Email không hợp lệ");
			return "_user_infor";
		} else {
			String url = cloudinaryService.uploadMultipleFile(file);
			user.setAvaImg(url);
			khDAO.save(user);
		}

		return "_user_infor";
	}

	public boolean isSoDienThoaiExists(String soDienThoai) {
		Optional<KhachHang> khachHang = getList().stream().filter(kh -> kh.getSdt().equals(soDienThoai)).findFirst();
		return khachHang.isPresent();
	}

	public boolean isEmailExists(String email) {
		return khDAO.findByEmail(email).isPresent();
	}
}
