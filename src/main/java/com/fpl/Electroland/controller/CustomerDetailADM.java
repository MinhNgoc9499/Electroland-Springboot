package com.fpl.Electroland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomerDetailADM {
    @GetMapping("/adminCustomerDetail")
	public String adminCustomerDetail() {
		return "CustomerDetailADM";
	}    
}
