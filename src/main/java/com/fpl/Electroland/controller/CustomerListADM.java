package com.fpl.Electroland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.helper.Author;

@Controller
public class CustomerListADM {

    @Autowired
    private KhachHangDAO khDao;

    @Autowired
    private Author author;

    @GetMapping("/adminCustomerList")
    public String adminCustomerList(
            Model model,
            Pageable pageable,
            @RequestParam(name = "hoTen", required = false) String hoTen,
            @RequestParam(name = "sdt", required = false) String sdt,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "diaChi", required = false) String diaChi,
            @RequestParam(name = "trangThai", required = false) Boolean trangThai) {
    
        // Gọi phương thức lọc từ DAO
        Page<Object[]> results = khDao.findFilteredCustomers(hoTen, sdt, email, diaChi, trangThai, pageable);
    
        // Thêm dữ liệu vào model
        model.addAttribute("customerList", results.getContent());
        model.addAttribute("currentPage", pageable.getPageNumber());
        model.addAttribute("totalPages", results.getTotalPages());
        model.addAttribute("totalItems", results.getTotalElements());
    
        return "CustomerList"; // Tên view hiển thị danh sách khách hàng
    }
    
}
