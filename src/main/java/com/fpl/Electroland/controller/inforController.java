package com.fpl.Electroland.controller;


import com.fpl.Electroland.dao.ChiTietDhDAO;
import com.fpl.Electroland.dao.CloudinaryService;
import com.fpl.Electroland.dao.DiaChiDAO;
import com.fpl.Electroland.dao.DonHangDAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dto.DeleteRequest;
import com.fpl.Electroland.dto.DiaChiDto;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.ChiTietDh;
import com.fpl.Electroland.model.DiaChi;
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
    @Autowired
	private DiaChiDAO diachiDAO;
	@ModelAttribute("user")
	public KhachHang getUser() {
		return author.getUserKhachHang();
	}

	@GetMapping("/infor")
	public String getIndexPage(Model model) {
		return "_user_infor";
	}

 	@GetMapping("/user_address")
    public String getListDiaChi(@ModelAttribute("user") KhachHang user,  
                                 Model model) {
		int userId = author.getUserKhachHang().getId();
        // Lấy danh sách địa chỉ của khách hàng
        List<DiaChi> diaChiList = diachiDAO.findByKhachHangId(userId);

        // Thêm thông tin vào model để truyền cho view
        model.addAttribute("diaChiList", diaChiList);
        model.addAttribute("user", user);

        return "_user_address"; // Trả về view để hiển thị
    }
	@PostMapping("/user_address/update")
public ResponseEntity<DiaChi> updateAddress(@ModelAttribute("user") KhachHang user, @RequestBody DiaChiDto diaChiDto) {
	if (diaChiDto.getId() == 0) {
		 // Tạo mới đối tượng DiaChi từ DTO
		 DiaChi diaChi = new DiaChi();
		 diaChi.setChiTiet(diaChiDto.getChiTiet());
		 diaChi.setLoaiDiaChi(diaChiDto.getLoaiDiaChi());
		 diaChi.setMacDinh(diaChiDto.isMacDinh());

		 diaChi.setHoTenNN(diaChiDto.getHoTen());
		 diaChi.setSdtNN(diaChiDto.getSdt());
		 // Thêm các thuộc tính khác của KhachHang nếu cần
		 diaChi.setKhachHang(user);
		// Nếu địa chỉ này được đánh dấu là mặc định, cập nhật tất cả các địa chỉ của khách hàng thành false
		if (diaChiDto.isMacDinh()) {
			List<DiaChi> allAddresses = diachiDAO.findByKhachHang(user);
			for (DiaChi address : allAddresses) {
				address.setMacDinh(false); // Đánh dấu tất cả các địa chỉ của khách hàng là không mặc định
				diachiDAO.save(address);  // Lưu các thay đổi
			}
}
		 // Lưu địa chỉ vào database
		 diachiDAO.save(diaChi);
 
		 return ResponseEntity.ok(diaChi);  
	}else{
        // Kiểm tra xem địa chỉ có tồn tại không
        Optional<DiaChi> diaChiUpdate = diachiDAO.findById(diaChiDto.getId());
            DiaChi existingDiaChi = diaChiUpdate.get();
            existingDiaChi.setChiTiet(diaChiDto.getChiTiet());
            existingDiaChi.setLoaiDiaChi(diaChiDto.getLoaiDiaChi());
            existingDiaChi.setMacDinh(diaChiDto.isMacDinh());

            // Cập nhật thông tin khách hàng
			existingDiaChi.setHoTenNN(diaChiDto.getHoTen());
			existingDiaChi.setSdtNN(diaChiDto.getSdt());
             // Nếu địa chỉ này được đánh dấu là mặc định, cập nhật tất cả các địa chỉ của khách hàng thành false
			 if (diaChiDto.isMacDinh()) {
                List<DiaChi> allAddresses = diachiDAO.findByKhachHang(user);
                for (DiaChi address : allAddresses) {
                    address.setMacDinh(false); // Đánh dấu tất cả các địa chỉ của khách hàng là không mặc định
                    diachiDAO.save(address);  // Lưu các thay đổi
                }
            }
            // Lưu địa chỉ và khách hàng (nếu thông tin khách hàng thay đổi)
            diachiDAO.save(existingDiaChi);
            
            return ResponseEntity.ok(existingDiaChi); // Trả về địa chỉ đã được cập nhật
	}
}

@DeleteMapping("/user_address/delete")
public ResponseEntity<String> deleteSelectedAddresses(@RequestBody DeleteRequest deleteRequest) {
	try {
		// Gọi service để xóa các địa chỉ theo danh sách ID
		diachiDAO.deleteByIdIn(deleteRequest.getIds());
		return ResponseEntity.ok("Đã xóa thành công các địa chỉ.");
	} catch (Exception e) {
		return ResponseEntity.status(500).body("Lỗi xảy ra khi xóa: " + e.getMessage());
	}
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
			if (!file.isEmpty()) {
				System.out.println("Tệp được chọn: " + file.getOriginalFilename()); // Log tên tệp
				if (!isImageFileType(file)) {
					model.addAttribute("error", "Chỉ cho phép  image files (jpg, jpeg, png, gif).");
					return "_user_infor";
				}
	
				// Log kiểu MIME của tệp để xem xét:
				System.out.println("Loại MIME tệp: " + file.getContentType()); // Kiểm tra kiểu MIME
	
				// Tải tệp lên
				String url = cloudinaryService.uploadMultipleFile(file);
				System.out.println("URL sau khi tải lên: " + url); // Log URL trả về từ Cloudinary
	
				user.setAvaImg(url);
				khDAO.save(user);
			} else {
				System.out.println("Không có tệp được chọn!");
			}
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
	private boolean isImageFileType(MultipartFile file) {
		String fileName = file.getOriginalFilename().toLowerCase();
		return fileName.endsWith(".jpg") || fileName.endsWith(".jpeg") || fileName.endsWith(".png") || fileName.endsWith(".gif");
	}
	
}
