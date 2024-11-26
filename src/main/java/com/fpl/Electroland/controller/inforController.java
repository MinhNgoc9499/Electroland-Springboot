package com.fpl.Electroland.controller;

import com.fpl.Electroland.dao.ChiTietDhDAO;
import com.fpl.Electroland.dao.CloudinaryService;
import com.fpl.Electroland.dao.DonHangDAO;

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
import com.fpl.Electroland.model.ChiTietDh;
import com.fpl.Electroland.model.DonHang;
import com.fpl.Electroland.model.KhachHang;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class inforController {

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
    @Autowired
	private ChiTietDhDAO chiTietDhDAO;
	@Autowired
	private DonHangDAO donhangDAO;

	@ModelAttribute("user")
	public KhachHang getUser() {
		return author.getUserKhachHang();
	}

	@GetMapping("/infor")
	public String getIndexPage(Model model) {
		return "_user_infor";
	}

	@GetMapping("/user_address")
	public String getAddress(Model model) {
		return "_user_address";
	}

	 @GetMapping("/order_detail")
    public String getOrderDetail(@ModelAttribute("user") KhachHang user, @RequestParam("id") int orderId, Model model) {
		KhachHang UserInfor = author.getUserKhachHang();
        // Lấy thông tin đơn hàng
		DonHang donHang = donhangDAO.findById(orderId);
		if (donHang == null) {
    	throw new RuntimeException("Đơn hàng không tồn tại");
		}if (donHang.getMaGiamDh() == null) {
			model.addAttribute("discount", "Không có giảm giá");
		} else {
			model.addAttribute("discount", donHang.getMaGiamDh().getGiamGiaVND());
		}


        // Lấy danh sách chi tiết đơn hàng
        List<ChiTietDh> chiTietDonHang = chiTietDhDAO.findByDonHangId(orderId);

        // Tính tổng giá trị đơn hàng
        double tongGiaTri = chiTietDonHang.stream()
                .mapToDouble(ct -> ct.getGiaBan() * ct.getSoLuong())
                .sum();

        // Truyền thông tin vào model
		model.addAttribute("UserInfor", UserInfor);
        model.addAttribute("donHang", donHang);
        model.addAttribute("chiTietDonHang", chiTietDonHang);
        model.addAttribute("tongGiaTri", tongGiaTri);

		return "_user_order_history_detail";
    }
    @GetMapping("/order_history")
public String getOrdersByStatus(@ModelAttribute("user") KhachHang user, Model model) {
    int userId = author.getUserKhachHang().getId();

    // Lấy đơn hàng hoàn thành
    List<Object[]> completedOrders = chiTietDhDAO.findOrdersByCustomerIdAndStatus(userId, 1);

    // Lấy đơn hàng đang xử lý
    List<Object[]> processingOrders = chiTietDhDAO.findOrdersByCustomerIdAndStatus(userId, 2);

    // Lấy đơn hàng đã hủy
    List<Object[]> cancelledOrders = chiTietDhDAO.findOrdersByCustomerIdAndStatus(userId, 0);
    // Thêm vào model để hiển thị
    model.addAttribute("completedOrders", completedOrders);
    model.addAttribute("processingOrders", processingOrders);
    model.addAttribute("cancelledOrders", cancelledOrders);

    return "_user_order_history";
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
