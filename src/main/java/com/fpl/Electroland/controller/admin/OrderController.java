// package com.fpl.Electroland.controller.admin;

// import com.fpl.Electroland.dao.DonHangDAO;
// import com.fpl.Electroland.model.DonHang;
// import com.fpl.Electroland.service.DonHangService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Page;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;


// @Controller
// @RequestMapping("/admin/order")
// public class OrderController {

//     @Autowired
//     private DonHangService donHangService;

//     @GetMapping()
//     public String orderDashboard(Model model,
//         @RequestParam(defaultValue = "0") int page,
//         @RequestParam(defaultValue = "20") int size,
//         @RequestParam(defaultValue = "") String searchOrderId,
//         @RequestParam(defaultValue = "") String searchCustomerName,
//         @RequestParam(defaultValue = "") String searchPhoneNumber,
//         @RequestParam(defaultValue = "") String searchAddress,
//         @RequestParam(required = false) String paymentMethod,
//         @RequestParam(required = false) String status
//     ) {
//         Page<DonHang> orders = donHangService.pageDonHang(searchOrderId, searchCustomerName, searchPhoneNumber,
//                                                           searchAddress, paymentMethod, status, page, size);
//         model.addAttribute("orders", orders.getContent());
//         model.addAttribute("currentPage", page);
//         model.addAttribute("totalPages", orders.getTotalPages());
//         model.addAttribute("searchOrderId", searchOrderId);
//         model.addAttribute("searchCustomerName", searchCustomerName);
//         model.addAttribute("searchPhoneNumber", searchPhoneNumber);
//         model.addAttribute("searchAddress", searchAddress);
//         model.addAttribute("paymentMethod", paymentMethod);
//         model.addAttribute("status", status);

//         return "OrderList";
//     }
// }
