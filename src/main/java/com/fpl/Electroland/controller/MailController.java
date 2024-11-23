package com.fpl.Electroland.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpl.Electroland.helper.MailerServiceImpl;

import ch.qos.logback.core.model.Model;
import jakarta.mail.MessagingException;

@Controller
public class MailController {

    @Autowired
    MailerServiceImpl mailer;

    @ResponseBody
    @RequestMapping("/mailer/demo")
    public String demo(Model model) {
        try {
            mailer.send("anhtnps37214@fpt.edu.vn", "Subject", "Body");
            return "OK";
        } catch (MessagingException e) {
            return e.getMessage();
        }
    }
}