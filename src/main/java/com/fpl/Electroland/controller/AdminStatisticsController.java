package com.fpl.Electroland.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpl.Electroland.dao.DonHangDAO;
import com.fpl.Electroland.dao.LoaiSanPhamDAO;
import com.fpl.Electroland.model.DonHang;
import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.SanPham;

@Controller
@RequestMapping("/admin")
public class AdminStatisticsController {

    @Autowired
    DonHangDAO donHangDAO;

    @Autowired
    LoaiSanPhamDAO loaiSPDAO;

    @GetMapping("/revenue-statistics")
    public String adminRevenueStatistics(Model model,
            @RequestParam(value = "sortTypeSP", required = false) Integer sortTypeSP,
            @RequestParam(value = "search", required = false) String search,
            // @RequestParam(value = "startDate", required = false) Date startDate,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page) {

        List<LoaiSanPham> listLoaiSP = loaiSPDAO.findAll();
        model.addAttribute("listLoaiSP", listLoaiSP);

        int pageSize = 20;
        Pageable pageable = PageRequest.of(page, pageSize);
        // if (startDate == null) {
        // LocalDate localStartDate = LocalDate.now();
        // startDate =
        // Date.from(localStartDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        // }

        // System.out.println("Start Date: " + startDate);

        if (sortTypeSP == null) {
        }
        if (search == null) {
            search = "";
        }

        Page<SanPham> listSP = donHangDAO.findRevenueByProductType(search, sortTypeSP, pageable);

        List<Integer> spIDS = listSP.stream()
                .map(SanPham::getId)
                .collect(Collectors.toList());
        System.out.println("Mã SP: " + spIDS);

        List<String> productNames = listSP.stream()
                .map(SanPham::getTenSP)
                .collect(Collectors.toList());
        // System.out.println("Tên SP: " + productNames);

        List<String> productType = listSP.stream()
                .map(sp -> {
                    return sp.getLoaiSanPham() != null ? sp.getLoaiSanPham().getTenLoaiSP() : "Chưa phân loại";
                })
                .collect(Collectors.toList());
        // System.out.println("Product Types: " + productType);

        List<Integer> orderCounts = listSP.stream()
                .map(sp -> donHangDAO.countOrderProdcut(sp.getId()))
                .collect(Collectors.toList());
        // System.out.println("Order Counts: " + orderCounts);

        List<Double> totalAmounts = listSP.stream()
                .map(sp -> {
                    Double total = donHangDAO.sumTotalByProduct(sp.getId());
                    return total != null ? total : 0.0;
                })
                .collect(Collectors.toList());
        // System.out.println("Total Amounts: " + totalAmounts);

        model.addAttribute("spIDS", spIDS);
        model.addAttribute("productNames", productNames);
        model.addAttribute("productType", productType);
        model.addAttribute("orderCounts", orderCounts);
        model.addAttribute("totalAmounts", totalAmounts);

        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", listSP.getTotalPages());
        model.addAttribute("totalItems", listSP.getTotalElements());

        return "revenueStatistics";
    }

    @GetMapping("/customer-statistics")
    public String adminCustomerStatistics(Model model,
            @RequestParam(value = "minOrder", required = false, defaultValue = "0") Integer minOrder,
            @RequestParam(value = "sortTypeKH", required = false, defaultValue = "Khách hàng thường") String sortTypeKH,
            @RequestParam(value = "page", required = false, defaultValue = "0") int page) {

        int pageSize = 10;
        Pageable pageable = PageRequest.of(page, pageSize);

        System.out.println("Min Order: " + minOrder);
        Page<KhachHang> countOrderCustomerPage = donHangDAO.findCustomersWithMoreThanMinOrders(minOrder, sortTypeKH,
                pageable);

        System.out.println(countOrderCustomerPage);

        List<Integer> customerIds = countOrderCustomerPage.stream()
                .map(KhachHang::getId)
                .collect(Collectors.toList());

        System.out.println("Customer IDs: " + customerIds);

        List<String> customerNames = countOrderCustomerPage.stream()
                .map(KhachHang::getHoTen)
                .collect(Collectors.toList());

        System.out.println("Customer Names: " + customerNames);

        List<Integer> orderCounts = countOrderCustomerPage.stream()
                .map(kh -> donHangDAO.countOrdersByCustomer(kh.getId()))
                .collect(Collectors.toList());

        System.out.println("Order Counts: " + orderCounts);

        List<Double> totalAmounts = countOrderCustomerPage.stream()
                .map(kh -> {
                    Double total = donHangDAO.sumTotalSalesByCustomer(kh.getId());
                    return total != null ? total : 0.0;
                })
                .collect(Collectors.toList());
        System.out.println("Total Amounts: " + totalAmounts);

        List<String> customerType = countOrderCustomerPage.stream()
                .map(kh -> {
                    return kh.getLoaiKhachHang() != null ? kh.getLoaiKhachHang().getTenLoai() : "Chưa phân loại";
                })
                .collect(Collectors.toList());

        System.out.println("Customer Types: " + customerType);

        model.addAttribute("customerType", customerType);
        model.addAttribute("customerIds", customerIds);
        model.addAttribute("customerNames", customerNames);
        model.addAttribute("orderCounts", orderCounts);
        model.addAttribute("totalAmounts", totalAmounts);
        model.addAttribute("minOrder", minOrder);
        model.addAttribute("sortTypeKH", sortTypeKH);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", countOrderCustomerPage.getTotalPages());
        model.addAttribute("totalItems", countOrderCustomerPage.getTotalElements());

        return "customerStatistics";
    }

    @GetMapping("/order-statistics")
    public String adminOrderStatistics(Model model,
            @RequestParam(value = "sortYear", required = false, defaultValue = "2024") int year) {
        List<Integer> successOrdersByMonth = new ArrayList<>();
        List<Integer> failedOrdersByMonth = new ArrayList<>();
        List<Integer> totalOrderByMonth = new ArrayList<>();
        List<Double> totalSales = new ArrayList<>();
        double totalRevenue = 0.0;

        for (int i = 1; i <= 12; i++) {
            Double totalSalesForMonth = donHangDAO.sumTotalSalesByYear(i, year);
            if (totalSalesForMonth == null) {
                totalSalesForMonth = 0.0;
            }
            List<DonHang> listDonByMonth = donHangDAO.findByMonthYear(i, year);
            long successCount = listDonByMonth.stream()
                    .filter(dh -> dh.getTrangThai() == 1)
                    .count();

            long failedCount = listDonByMonth.stream()
                    .filter(dh -> dh.getTrangThai() == 0)
                    .count();

            failedOrdersByMonth.add((int) failedCount);
            successOrdersByMonth.add((int) successCount);
            totalOrderByMonth.add(listDonByMonth.size());
            System.out.println(totalOrderByMonth);
            totalSales.add(totalSalesForMonth);
            System.out.println("Tháng " + i + ": " + "Doanh thu = " + totalSalesForMonth);
            totalRevenue += totalSalesForMonth;
        }

        model.addAttribute("totalOrderByMonth", totalOrderByMonth);
        model.addAttribute("totalSales", totalSales);
        model.addAttribute("successOrdersByMonth", successOrdersByMonth);
        model.addAttribute("failedOrdersByMonth", failedOrdersByMonth);
        model.addAttribute("totalRevenue", totalRevenue);
        return "OrderStatisticsADM";
    }
}
