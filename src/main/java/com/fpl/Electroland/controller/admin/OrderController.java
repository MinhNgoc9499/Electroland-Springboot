package com.fpl.Electroland.controller.admin;

import com.fpl.Electroland.dao.ChiTietDhDAO;
import com.fpl.Electroland.dao.DonHangDAO;
import com.fpl.Electroland.dto.OrderDtoRequest;
import com.fpl.Electroland.model.ChiTietDh;
import com.fpl.Electroland.model.DonHang;
import com.fpl.Electroland.service.DonHangService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequestMapping("/admin/order")
public class OrderController {

    @Autowired
    private DonHangDAO donhangDAO;
    @Autowired
    private DonHangService donHangService;
    @Autowired
    private ChiTietDhDAO chiTietDhDAO;

    @GetMapping()
    public String orderDashboard(
        Model model,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "20") int size,
        @RequestParam(defaultValue = "") String searchOrderId,
        @RequestParam(defaultValue = "") String searchCustomerName,
        @RequestParam(defaultValue = "") String searchPhoneNumber,
        @RequestParam(defaultValue = "") String searchAddress,
        @RequestParam(defaultValue = "") String paymentMethod,
        @RequestParam(defaultValue = "") String status
    ) {

        Page<DonHang> orders = donHangService.pageDonHang(searchOrderId, searchCustomerName, searchPhoneNumber,
            searchAddress, paymentMethod, status, page, size);

        model.addAttribute("orders", orders.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orders.getTotalPages());
        model.addAttribute("searchOrderId", searchOrderId);
        model.addAttribute("searchCustomerName", searchCustomerName);
        model.addAttribute("searchPhoneNumber", searchPhoneNumber);
        model.addAttribute("searchAddress", searchAddress);
        model.addAttribute("paymentMethod", paymentMethod);
        model.addAttribute("status", status);
        return "OrderList";
    }

    @GetMapping("/detail/{id}")
    public String oderDetail(Model model, @PathVariable int id) {
        DonHang donHang = donhangDAO.findById(id);
        List<ChiTietDh> orders = chiTietDhDAO.findByDonHangId(donHang.getId());
        model.addAttribute("order", donHang);
        model.addAttribute("orders", orders);
        return "OrderListDetail";
    }

    @PostMapping("/detail/{id}/update")
    public String updateOrder(@RequestBody OrderDtoRequest orderDtoRequest, @PathVariable String id) {
        donHangService.updateDonHang(orderDtoRequest);
        return "/admin/order/detail/" + id;
    }
}
