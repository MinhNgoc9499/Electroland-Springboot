package com.fpl.Electroland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/index")
    public String adminIndex() {
        return "dashboard";
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

    @GetMapping("/employes")
    public String adimEmployes() {
        return "employessADM";
    }

    @GetMapping("/EmployessDetail	")
    public String adimEmployesDetail() {
        return "EmployessDetailADM";
    }

}
