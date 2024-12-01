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
            Pageable pageable, // Sử dụng Pageable để nhận thông tin phân trang
            @RequestParam(name = "search", required = false) String search, // Optional search parameter nếu cần
            @RequestParam(name = "size", defaultValue = "10") int size

    ) {
        // Gọi phương thức phân trang từ DAO
        Page<Object[]> results = khDao.findCustomersWithAddresses(pageable);

        // Thêm dữ liệu vào model
        model.addAttribute("customerList", results.getContent()); // Dữ liệu khách hàng
        model.addAttribute("currentPage", pageable.getPageNumber()); // Trang hiện tại
        model.addAttribute("totalPages", results.getTotalPages()); // Tổng số trang
        model.addAttribute("totalItems", results.getTotalElements()); // Tổng số bản ghi

        return "CustomerList"; // Tên view hiển thị danh sách khách hàng
    }
}
