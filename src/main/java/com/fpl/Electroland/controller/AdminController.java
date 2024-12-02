package com.fpl.Electroland.controller;

import com.fpl.Electroland.dao.DonHangDAO;
import java.util.List;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.DonHang;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    Author author;

    @Autowired
    private DonHangDAO donHangDAO;

    @GetMapping("/index")
    public String getReport(
            @RequestParam(value = "month", defaultValue = "#{T(java.time.LocalDate).now().getMonthValue()}") String monthParam,
            @RequestParam(value = "year", defaultValue = "#{T(java.time.LocalDate).now().getYear()}") String yearParam,
            @RequestParam(value = "continue", required = false) String continueParam,
            Model model) {

        try {
            // Parse month and year
            int month = Integer.parseInt(monthParam);
            int year = Integer.parseInt(yearParam);

            // Handle the "continue" parameter if needed
            if (continueParam != null) {
                System.out.println("Continue parameter: " + continueParam);
            }

            // Call the necessary methods to get data
            Long totalOrders = donHangDAO.tinhTongDonHangTheoNamvaThang(year, month);
            Long successfulTransactions = donHangDAO.tinhDonHangthanhcongTheoNamvaThang(year, month);
            Long canceledOrders = donHangDAO.tinhDonHangBiHuyTheoNamvaThang(year, month);
            Long totalRevenue = donHangDAO.getRevenueByMonthAndYear(year, month);
            // Get the quarterly data
            List<Object[]> quarterData = donHangDAO.findTotalOrdersByQuarter(year);
            double[] quarterDataResult = new double[4]; // Array to hold quarterly data (Q1, Q2, Q3, Q4)
            // Processing the quarter data
            for (Object[] row : quarterData) {
                String quarter = (String) row[0]; // The quarter (e.g., "Quý 1")
                Long count = (Long) row[1]; // Số lượng đơn hàng hoặc doanh thu
                double revenue = count != null ? count.doubleValue() : 0.0; // Đảm bảo giá trị không phải là null

                // Gán doanh thu vào mảng kết quả tương ứng với quý
                switch (quarter) {
                    case "Quý 1":
                        quarterDataResult[0] = revenue;
                        break;
                    case "Quý 2":
                        quarterDataResult[1] = revenue;
                        break;
                    case "Quý 3":
                        quarterDataResult[2] = revenue;
                        break;
                    case "Quý 4":
                        quarterDataResult[3] = revenue;
                        break;
                    default:
                        // Xử lý trường hợp quý không xác định nếu cần
                        break;
                }
            }
            // Lấy doanh thu cho từng tháng của năm hiện tại
            List<Object[]> revenueDataThisYear = donHangDAO.getRevenueByMonth(year);

            // Lấy doanh thu cho từng tháng của năm trước
            List<Object[]> revenueDataLastYear = donHangDAO.getRevenueByMonth(year - 1);

            // Khởi tạo mảng doanh thu cho từng tháng (12 tháng)
            double[] monthlyRevenueResultThisYear = new double[12]; // Cho năm này
            double[] monthlyRevenueResultLastYear = new double[12]; // Cho năm trước

            // Dữ liệu doanh thu của năm này
            for (Object[] row : revenueDataThisYear) {
                int monthFromData = (Integer) row[0]; // Tháng
                Double revenue = (Double) row[1]; // Doanh thu, có thể là null
                // Nếu revenue là null, gán giá trị là 0.0
                monthlyRevenueResultThisYear[monthFromData - 1] = (revenue != null) ? revenue : 0.0;
            }

            // Dữ liệu doanh thu của năm trước
            for (Object[] row : revenueDataLastYear) {
                int monthFromData = (Integer) row[0]; // Tháng
                Double revenue = (Double) row[1]; // Doanh thu, có thể là null
                // Nếu revenue là null, gán giá trị là 0.0
                monthlyRevenueResultLastYear[monthFromData - 1] = (revenue != null) ? revenue : 0.0;
            }

            // Thêm vào model để hiển thị trên frontend (nếu sử dụng Spring MVC)
            model.addAttribute("monthlyRevenueResultThisYear", monthlyRevenueResultThisYear);
            model.addAttribute("monthlyRevenueResultLastYear", monthlyRevenueResultLastYear);

            model.addAttribute("quarterlyRevenue", quarterDataResult); // Pass JSON string to the model
            // Add other attributes to the model
            model.addAttribute("currentYear", year);
            model.addAttribute("currentMonth", month);
            model.addAttribute("totalRevenue", totalRevenue);
            model.addAttribute("totalOrders", totalOrders);
            model.addAttribute("successfulTransactions", successfulTransactions);
            model.addAttribute("canceledOrders", canceledOrders);

            return "dashboard"; // Return the Thymeleaf template name

        } catch (NumberFormatException e) {
            model.addAttribute("error", "Invalid year or month value provided. Please check the input.");
            return "error"; // Return error page if parsing fails
        } catch (Exception e) {
            model.addAttribute("error", "An unexpected error occurred: " + e.getMessage());
            return "error"; // Return error page if an unexpected error occurs
        }
    }

    @GetMapping("/product")
    public String adminProduct() {
        return "productADM";
    }

    @GetMapping("/product-detail")
    public String adminProductDetail() {
        return "productDetailADM";
    }

    @GetMapping("/employess")
    public String adminEmployes() {
        return "employessADM";
    }

    @GetMapping("/EmployessDetail	")
    public String adimEmployesDetail() {
        return "EmployessDetailADM";
    }
}
