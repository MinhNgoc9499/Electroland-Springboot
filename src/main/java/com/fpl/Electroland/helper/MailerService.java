package com.fpl.Electroland.helper;

import com.fpl.Electroland.model.Mail;

import jakarta.mail.MessagingException;

public interface MailerService {
    /**
     * Gửi email
     * 
     * @param mail thông tin email
     * @throws MessagingException lỗi gửi email
     */
    void send(Mail mail) throws MessagingException;

    /**
     * Gửi email đơn giản
     * 
     * @param to      email người nhận
     * @param subject tiêu đề email
     * @param body    nội dung email
     * @throws MessagingException lỗi gửi email
     */
    void send(String to, String subject, String body) throws MessagingException;
}