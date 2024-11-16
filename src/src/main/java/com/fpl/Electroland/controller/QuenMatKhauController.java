package com.fpl.Electroland.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class QuenMatKhauController {
	@GetMapping("/quenMatKhau")
	public String quenMatKhau(Model model) {
		return "QuenMatKhau";
	}
	
}
