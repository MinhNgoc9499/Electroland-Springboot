package com.fpl.Electroland.restController;

import java.lang.module.ModuleDescriptor.Builder;
import java.net.http.HttpRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fpl.Electroland.dao.GioHangDAO;
import com.fpl.Electroland.dao.GioHangThuocTinhDAO;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.MaGiamDhDAO;
import com.fpl.Electroland.dao.MaGiamKhDAO;
import com.fpl.Electroland.dao.MaGiamSpDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.GioHangThuocTinh;
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

    @Autowired
    GioHangThuocTinhDAO gioHangThuocTinhDAO;

    // Xóa sản phẩm khỏi giỏ hàng (Delete Product from Cart)
    @GetMapping("/rest/giohang/{id}")
    public void deleteProductFromCart(@PathVariable("id") int id) {
        gioHangThuocTinhDAO.deleteAllByGioHang(gioHangDAO.findById(id).get());
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
        Map<GioHang, Double> list = getList();
        Double total = 0.0;
        for (GioHang gh : list.keySet()) {
            total += list.get(gh) * gh.getSoLuong();
            System.out.println(total);
        }
        return total;
    }

    public Map<GioHang, Double> getList() {
        Map<GioHang, Double> map = new HashMap<>();
        List<GioHang> list = new ArrayList<>();
        list = gioHangDAO.findByKhachHang(author.getUserKhachHang());
        list.forEach(e -> {
            Double total = e.getSanPham().getGiaGiam();
            List<GioHangThuocTinh> ghttlist = gioHangThuocTinhDAO.findByGioHang(e);
            for (GioHangThuocTinh ghtt : ghttlist) {
                if (ghtt.getMauSp() != null) {
                    total += ghtt.getMauSp().getGiaTri();
                } else
                    total += ghtt.getThuocTinh().getGiaTri();
            }
            ;
            map.put(e, total);
        });
        return map;
    }

    public List<GioHang> listSelected() {
        return gioHangDAO.findAllByKhachHangAndChecked(author.getUserKhachHang(), true);
    }

    @GetMapping("/rest/giohang/checkDiscount")
    public ResponseEntity<String> checkDiscount() {
        Double TotalMoney = getTotal();
        List<MaGiamKh> list = mgkhDao.findByKhachHangAndChecked(author.getUserKhachHang(), true);
        StringBuilder mess = new StringBuilder("");
        System.out.println(TotalMoney == 0);
        for (MaGiamKh maGiamKh : list) {
            if (maGiamKh.getMaGiamDh() != null) {
                if (TotalMoney < maGiamKh.getMaGiamDh().getMinDonGia() || TotalMoney == 0) {
                    MaGiamKh mg = maGiamKh;
                    mg.setChecked(false);
                    mgkhDao.save(mg);
                    mgkhDao.flush();
                    mess.append(
                            "<p style='text-align: center;color: red;'>Đã xóa mã giảm đơn hàng do không đủ điều điện.</p>");
                }
            } else {
                List<GioHang> gh = gioHangDAO.findBySanPhamAndKhachHangAndCheckedTrue(
                        maGiamKh.getMaGiamSp().getSanPham(),
                        author.getUserKhachHang());
                if (gh.isEmpty()) {
                    MaGiamKh mg = maGiamKh;
                    mg.setChecked(false);
                    mgkhDao.save(mg);
                    mgkhDao.flush();
                    mess.append(
                            "<p style='text-align: center;color: red;'>Đã xóa mã giảm sản phẩm do không đủ điều điện.</p>");
                } else if (mgkhDao.getMGSPChecked(author.getUserKhachHang(), maGiamKh.getMaGiamSp().getSanPham())
                        .size() > 1) {
                    MaGiamKh mg = maGiamKh;
                    mg.setChecked(false);
                    mgkhDao.save(mg);
                    mgkhDao.flush();
                    mess.append(
                            "<p style='text-align: center;color: red;'>Đã xóa mã giảm sản phẩm do 1 sản phẩm chỉ được áp dụng 1 voucher.</p>");
                }
            }
        }
        ;
        return ResponseEntity.ok(mess.toString());
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
        MaGiamKh mgkh = mgkhDao.findById(id).get();
        if (mgkh.getMaGiamSp() == null) {
            Optional<MaGiamKh> mg = mgkhDao.findByKhachHangAndMaGiamSpIsNullAndCheckedTrue(author.getUserKhachHang());
            if (mg.isPresent()) {
                MaGiamKh mg1 = mg.get();
                mg1.setChecked(false);
                mgkhDao.save(mg1);
                mgkhDao.flush();
            }
        }
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