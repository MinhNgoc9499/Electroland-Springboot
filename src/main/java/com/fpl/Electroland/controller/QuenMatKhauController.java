package com.fpl.Electroland.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.helper.OtpService;
import com.fpl.Electroland.model.KhachHang;

import jakarta.servlet.http.HttpSession;

@Controller
public class QuenMatKhauController {
    
    @Autowired
    private KhachHangDAO khachHangDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JavaMailSender mailSender; // Sử dụng JavaMailSender để gửi email
    
    @Autowired
    private OtpService otpService; //Sử dụng OtpService

    
    @Autowired
    private HttpSession session;  // Sử dụng session để lưu OTP tạm thời

    @ModelAttribute("khachHang")
	public KhachHang newKH() {
		return new KhachHang();
	}

    // Hiển thị trang quên mật khẩu
    @GetMapping("/quenMatKhau")
    public String quenMatKhau(Model model) {
        return "QuenMatKhau";
    }

    // Xử lý yêu cầu quên mật khẩu (gửi email OTP)
    @PostMapping("/quenMatKhau")
    public String processForgotPassword(@RequestParam("email") String email, Model model) {
    Optional<KhachHang> khachHang = khachHangDAO.findByEmail(email);
    if (khachHang.isPresent()) {
        String otp = otpService.generateOtp(); //*
        otpService.sendOtpEmail(email, otp); //*
        session.setAttribute("otpTimestamp", System.currentTimeMillis()); // Lưu thời gian sinh OTP *
        
        session.setAttribute("otp", otp);
        session.setAttribute("email", email);
        model.addAttribute("message", "OTP đã được gửi đến email của bạn.");
        model.addAttribute("otpVisible", true);  // Hiển thị form OTP
    } else {
        model.addAttribute("error", "Email không tồn tại.");
    }
    return "QuenMatKhau";
}

    // Xử lý cập nhật mật khẩu mới
    @PostMapping("/capNhatMatKhau")
    public String updatePassword(@RequestParam("email") String email, @RequestParam("newPassword") String newPassword, @RequestParam("otp") String otp, Model model) {
        String sessionOtp = (String) session.getAttribute("otp");
        String sessionEmail = (String) session.getAttribute("email");
        Long otpTimestamp = (Long) session.getAttribute("otpTimestamp");//**
     // Kiểm tra OTP và thời gian hết hạn **
        if (sessionOtp != null && sessionEmail != null && sessionOtp.equals(otp) && sessionEmail.equals(email)) {
        	// Kiểm tra xem OTP có hết hạn không (5 phút)**
            if (System.currentTimeMillis() - otpTimestamp > 5 * 60 * 1000) {  // 5 phút hết hạn
                model.addAttribute("error", "Mã OTP đã hết hạn.");
                return "QuenMatKhau";
            }
            Optional<KhachHang> khachHang = khachHangDAO.findByEmail(email);
            if (khachHang.isPresent()) {
                // Mã hóa mật khẩu mới
                KhachHang updatedKhachHang = khachHang.get();
                updatedKhachHang.setMatKhau(passwordEncoder.encode(newPassword));
                khachHangDAO.save(updatedKhachHang);

                // Xóa OTP trong session sau khi cập nhật mật khẩu thành công
                session.removeAttribute("otp");
                session.removeAttribute("email");
                session.removeAttribute("otpTimestamp"); //**

                model.addAttribute("message", "Mật khẩu đã được cập nhật thành công.");
            } else {
                model.addAttribute("error", "Email không tồn tại.");
            }
        } else {
            model.addAttribute("error", "Mã OTP không chính xác hoặc hết hạn.");
        }

        return "QuenMatKhau";
    }

//    // Hàm sinh OTP ngẫu nhiên
//    private String generateOtp() {
//        int otp = 100000 + (int)(Math.random() * 900000); // Sinh OTP 6 chữ số
//        return String.valueOf(otp);
//    }
//
//    // Hàm gửi OTP qua email
//    private void sendOtpEmail(String email, String otp) {
//        SimpleMailMessage message = new SimpleMailMessage();
//        message.setTo(email);
//        message.setSubject("Mã OTP xác nhận thay đổi mật khẩu");
//        message.setText("Mã OTP của bạn là: " + otp);
//        
//        // Gửi email
//        mailSender.send(message);
//    }

}