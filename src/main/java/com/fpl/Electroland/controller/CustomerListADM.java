package com.fpl.Electroland.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpl.Electroland.dao.DiaChiDAO;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.KhachHang;

@Controller
public class CustomerListADM {

    @Autowired
    KhachHangDAO khDao;

    @Autowired
    Author author;
	@Autowired
    DiaChiDAO diachiDao;

    // Lấy danh sách khách hàng
    @ModelAttribute("List")
    public List<KhachHang> getList() {
        return khDao.findAll(); // Lấy toàn bộ khách hàng từ database
    }

    // Hiển thị danh sách khách hàng
    @GetMapping("/adminCustomerList")
    public String adminCustomerList() {
        return "CustomerList";
    }

  

  // Hiển thị form chỉnh sửa khách hàng
	@GetMapping("/adminCustomerList/Edit/{id}")
	public String showEditForm(@PathVariable int id, Model model) {
    KhachHang khachHang = khDao.findById(id).orElse(null); // Trả về null nếu không tìm thấy
    if (khachHang == null) {
        model.addAttribute("error", "Không tìm thấy khách hàng!");
        return "errorPage";
    }
    model.addAttribute("customer", khachHang);
    return "CustomerDetailADM";
}


    // Cập nhật thông tin khách hàng
    @PostMapping("/adminCustomerList/Edit/{id}")
    public String updateCustomer(@PathVariable int id, @Validated @ModelAttribute("customer") KhachHang khachHang,BindingResult rs) {
			KhachHang kh = khDao.findById(id).get();
			kh.setTrangThai(khachHang.getTrangThai());
            khDao.save(kh); // Lưu thay đổi vào database
        return "redirect:/adminCustomerList";
    }

	@GetMapping("/adminCustomerList/Delete/{id}")
	public String deleteCustomer(@PathVariable("id") int id) {
		khDao.deleteById(id);
		return "redirect:/adminCustomerList";
	}
	
	
	
}
