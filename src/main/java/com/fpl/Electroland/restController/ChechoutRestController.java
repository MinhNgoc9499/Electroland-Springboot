package com.fpl.Electroland.restController;

import org.springframework.web.bind.annotation.RestController;

import com.fpl.Electroland.dao.DonHangDAO;
import com.fpl.Electroland.model.DonHang;

import io.micrometer.core.ipc.http.HttpSender.Request;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
public class ChechoutRestController {

    @Autowired
    DonHangDAO donHangDAO;

    @Autowired
    HttpServletResponse response;

    @Autowired
    HttpServletRequest request;

    @GetMapping("/Order_Success")
    public void Order_Success(@RequestBody DonHang donHang) throws IOException {
        donHangDAO.save(donHang);
        response.sendRedirect("http://localhost:8080/index");
    }
}
