package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fpl.Electroland.dao.DonHangDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.DonHang;



@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    Author author;

    @Autowired
    DonHangDAO donHangDAO;

    @GetMapping("/index")
     public String getReport() {
    	
        // // Call the service to get the data for the selected month
        // Long totalOrders = donHangDAO.countTotalOrdersByMonth(month);
        // Long successfulTransactions = donHangDAO.countSuccessfulTransactionsByMonth(month);
        // Long canceledOrders = donHangDAO.countCanceledOrdersByMonth(month);
        // // Double totalRevenue = reportService.getTotalRevenueByMonth(month);
        // // Get the monthly revenue data
        // // double[] monthlyRevenue = reportService.getMonthlyRevenue(); 

        // // Convert monthly revenue data to JSON format for chart
        // // String monthlyRevenueJson = new ObjectMapper().writeValueAsString(monthlyRevenue);

        // // Add attributes to the model
        // // model.addAttribute("monthlyRevenue", monthlyRevenueJson);
        // model.addAttribute("totalOrders", totalOrders);
        // model.addAttribute("successfulTransactions", successfulTransactions);
        // model.addAttribute("canceledOrders", canceledOrders);
        // // model.addAttribute("totalRevenue", totalRevenue);
        // model.addAttribute("currentMonth", month); // Pass selected month to the view
        // // Call the service to get the product revenue data
   
        // model.addAttribute("customerData", customerData);
        return "dashboard";  // Thymeleaf template name
    }

    @GetMapping("/product")
    public String adminProduct() {
        return "productADM";
    }

    @GetMapping("/product-detail")
    public String adminProductDetail() {
        return "productDetailADM";
    }

    @GetMapping("/order")
    public String adminOrderList() {
        return "OrderList";
    }

    @GetMapping("/employess")
    public String adminEmployes() {
        return "employessADM";
    }

    @GetMapping("/EmployessDetail	")
    public String adimEmployesDetail() {
        return "EmployessDetailADM";
    }

    public List<DonHang> getDonHangByTT(int trangThai){
        List<DonHang> list = new ArrayList<>();
        list = donHangDAO.findAll();
        list = list.stream().filter(dh -> dh.getTrangThai() == trangThai).collect(Collectors.toList());
        return list;
    }
    
    @GetMapping("/order-statistics")
    public String adminOrderStatistics(Model model) {
        List<DonHang> listDonHuyByMonth = donHangDAO.findByMonthYearAndTrangThai(1, 2020, 0);
        for(int i = 1 ; i <= 12; i++){
            listDonHuyByMonth = donHangDAO.findByMonthYearAndTrangThai(i, 2020, 0);
            System.out.println(listDonHuyByMonth.size() + " month: " + i);
            // model.addAttribute("listDonHuyByMonth", listDonHuyByMonth);
        }
        return "OrderStatisticsADM";
    }
}
