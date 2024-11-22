package com.fpl.Electroland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
	@GetMapping("/adminIndex")
	public String adminIndex() {
		return "dashboard";
	}

	@GetMapping("/adminProduct")
	public String adminProduct() {
		return "productADM";
	}

	@GetMapping("/adminProductDetail")
	public String adminProductDetail() {
		return "productDetailADM";
	}

	@GetMapping("/adminOrderList")
	public String adminOrderList() {
		return "OrderList";
	}

	@GetMapping("/adimEmployes")
	public String adimEmployes() {
		return "employessADM";
	}

	@GetMapping("/adminEmployessDetail	")
	public String adimEmployesDetail() {
		return "EmployessDetailADM";
	}

}
