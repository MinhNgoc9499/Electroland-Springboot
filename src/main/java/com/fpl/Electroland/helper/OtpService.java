package com.fpl.Electroland.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {
	@Autowired
    private JavaMailSender mailSender;

    // Tạo OTP
    public String generateOtp() {
        int otp = 100000 + (int)(Math.random() * 900000); // Sinh OTP 6 chữ số
        return String.valueOf(otp);
    }

    // Gửi OTP qua email
    public void sendOtpEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Mã OTP xác nhận thay đổi mật khẩu");
        message.setText("Mã OTP của bạn là: " + otp);
        mailSender.send(message);
    }

}
