package com.fpl.Electroland.restController;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fpl.Electroland.dao.GioHangDAO;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.MaGiamDhDAO;
import com.fpl.Electroland.dao.MaGiamKhDAO;
import com.fpl.Electroland.dao.MaGiamSpDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.MaGiamDh;
import com.fpl.Electroland.model.MaGiamKh;

import jakarta.servlet.http.HttpServletRequest;

@RestController //
public class GioHangRestCtrl {
    @Autowired
    LoaiKhachHangDAO dao;

    @Autowired
    GioHangDAO gioHangDAO; // DAO để thao tác với giỏ hàng

    @Autowired
    Author author;

    @Autowired
    SanPhamDAO spDAO;

    @Autowired
    KhachHangDAO khDAO;

    @Autowired
    MaGiamDhDAO mgdhDao;

    @Autowired
    MaGiamKhDAO mgkhDao;

    @Autowired
    MaGiamSpDAO mgspDao;

    // Xóa sản phẩm khỏi giỏ hàng (Delete Product from Cart)
    @GetMapping("/rest/giohang/{id}")
    public void deleteProductFromCart(@PathVariable("id") int id) {
        gioHangDAO.deleteById(id);
    }

    // Thêm sản phẩm vào giỏ hàng (Add Product to Cart)
    @GetMapping("/rest/giohang/update{checked}")
    public void updateProductFromCart(@PathVariable("checked") int id) {
        GioHang gh = gioHangDAO.findById(id).get();
        if (gh.getChecked() == true) {
            gh.setChecked(false);
        } else {
            gh.setChecked(true);
        }
        gioHangDAO.save(gh);
        gioHangDAO.flush();
    }

    @GetMapping("/rest/giohang/Vouchers")
    public List<MaGiamKh> getVouchers() {
        return mgkhDao.findByKhachHang(author.getUserKhachHang());
    }

    @GetMapping("/rest/giohang/getTotal")
    public Double getTotal() {
        Double total = 0.0;
        List<GioHang> list = gioHangDAO.findAllByKhachHangAndChecked(author.getUserKhachHang(), true);
        for (GioHang gh : list) {
            total += gh.getSanPham().getGiaGiam() * gh.getSoLuong();
        }
        return total;
    }

    public List<GioHang> listSelected() {
        return gioHangDAO.findAllByKhachHangAndChecked(author.getUserKhachHang(), true);
    }

    @GetMapping("/rest/giohang/checkDiscount")
    public String checkDiscount() {
        System.out.println("check");
        Double TotalMoney = getTotal();
        List<MaGiamKh> list = mgkhDao.findByKhachHangAndChecked(author.getUserKhachHang(), true);

        System.out.println(list);
        for (MaGiamKh maGiamKh : list) {
            if (maGiamKh.getMaGiamDh() != null) {
                if (TotalMoney < maGiamKh.getMaGiamDh().getMinDonGia()) {
                    MaGiamKh mg = maGiamKh;
                    mg.setChecked(false);
                    mgkhDao.save(mg);
                    mgkhDao.flush();

                    System.out.println("----------" + TotalMoney + "---------" + maGiamKh.getMaGiamDh().getMinDonGia());
                    System.out.println(mg);
                }
            } else {
                Optional<GioHang> gh = gioHangDAO.findBySanPhamAndKhachHang(maGiamKh.getMaGiamSp().getSanPham(),
                        author.getUserKhachHang());
                System.out.println(gh.isPresent());
                if (!gh.isPresent() || !gh.get().getChecked()) {
                    MaGiamKh mg = maGiamKh;
                    mg.setChecked(false);
                    mgkhDao.save(mg);
                    mgkhDao.flush();

                    System.out.println("----------" + TotalMoney + "---------" + maGiamKh.getMaGiamSp().getId());
                    System.out.println(mg);
                }
            }
        }
        ;
        System.out.println(mgkhDao.findByKhachHangAndChecked(author.getUserKhachHang(), true).size());
        return "";
    }

    @GetMapping("/rest/giohang/getDiscount")
    public Double getDiscount() {

        Double TotalMoney = getTotal();
        Double TotalDiscount = 0.0;
        List<MaGiamKh> list = mgkhDao.findByKhachHangAndChecked(author.getUserKhachHang(), true);
        for (MaGiamKh maGiamKh : list) {
            if (maGiamKh.getMaGiamDh() != null) {
                TotalDiscount += getDiscountDH(maGiamKh.getMaGiamDh(), TotalMoney);
            } else
                TotalDiscount += maGiamKh.getMaGiamSp().getGiaTri();
        }
        ;
        return TotalDiscount;
    }

    @GetMapping("/rest/giohang/updateVoucher/{id}")
    public void updateVoucherFromCart(@PathVariable("id") int id) {
        // Map<String, Object> response = new HashMap<>();
        System.out.println(id);
        MaGiamKh mgkh = mgkhDao.findById(id).get();
        System.out.println(id);
        mgkh.setChecked(!mgkh.getChecked());
        mgkhDao.save(mgkh);
        mgkhDao.flush(); // Lưu trạng thái mới vào DB
    }

    @GetMapping("/rest/giohang/Quantity")
    public void updateQuantity(@RequestParam("state") boolean state, @RequestParam("id") int id) {
        GioHang gh = gioHangDAO.findById(id).get();
        if (state) {
            gh.setSoLuong(gh.getSoLuong() + 1);
        } else
            gh.setSoLuong(gh.getSoLuong() - 1);
        gioHangDAO.save(gh);
    }

    public Double getDiscountDH(MaGiamDh maGiamDh, Double totalMoney) {
        if (maGiamDh.getGiamGiaVND() != null)
            return maGiamDh.getGiamGiaVND();
        else {
            if (maGiamDh.getPhanTramGG() * totalMoney > maGiamDh.getMaxGG()) {
                return maGiamDh.getMaxGG();
            } else
                return maGiamDh.getPhanTramGG() * totalMoney;
        }
    }

}