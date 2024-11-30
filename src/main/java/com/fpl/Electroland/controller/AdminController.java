package com.fpl.Electroland.controller;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpl.Electroland.helper.Author;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fpl.Electroland.dao.DonHangDAO;
import com.fpl.Electroland.model.DonHang;


@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    Author author;

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


}
