package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fpl.Electroland.dao.GioHangDAO;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.MaGiamDhDAO;
import com.fpl.Electroland.dao.MaGiamKhDAO;
import com.fpl.Electroland.dao.MaGiamSpDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.MaGiamDh;
import com.fpl.Electroland.model.MaGiamKh;
import com.fpl.Electroland.model.MaGiamSp;

import com.fpl.Electroland.model.NhaCungCap;
import com.fpl.Electroland.model.SanPham;

@Controller
public class gioHangController {

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

	@ModelAttribute("List")
	public List<GioHang> getList() {
		List<GioHang> list = new ArrayList<>();
		list = gioHangDAO.findByKhachHang(author.getUserKhachHang());
		return list;
	}

	@ModelAttribute("Voucher")
	public List<MaGiamKh> getVoucher() {
		// Lấy danh sách mã giảm giá của khách hàng
		List<MaGiamKh> vouchers = mgkhDao.findByKhachHang(author.getUserKhachHang());
		Double total = gettotal();

		// Sử dụng Set để theo dõi các sản phẩm đã được áp dụng voucher
		Set<Integer> appliedProductIds = new HashSet<>();

		for (MaGiamKh voucher : vouchers) {
			MaGiamDh maGiamDh = voucher.getMaGiamDh();
			MaGiamSp maGiamSp = voucher.getMaGiamSp();
			boolean validVoucher = false;

			// Kiểm tra điều kiện mã giảm giá đơn hàng
			if (maGiamDh != null) {
				boolean validGiamGiaVND = maGiamDh.getGiamGiaVND() != null
						&& total >= (maGiamDh.getMinDonGia() != null ? maGiamDh.getMinDonGia() : 0)
						&& total <= (maGiamDh.getMaxGG() != null ? maGiamDh.getMaxGG() : Double.MAX_VALUE);

				boolean validPhanTramGG = maGiamDh.getPhanTramGG() != null
						&& total >= (maGiamDh.getMinDonGia() != null ? maGiamDh.getMinDonGia() : 0)
						&& total <= (maGiamDh.getMaxGG() != null ? maGiamDh.getMaxGG() : Double.MAX_VALUE);

				validVoucher = validGiamGiaVND || validPhanTramGG;
			}

			// Kiểm tra điều kiện mã giảm giá sản phẩm
			if (maGiamSp != null) {
				boolean validSanPham = maGiamSp.getGiaTri() != null && maGiamSp.getSanPham() != null;

				// Kiểm tra xem sản phẩm đã được áp dụng voucher chưa
				if (validSanPham) {
					// Kiểm tra xem ID của sản phẩm đã có voucher nào được áp dụng chưa
					if (!appliedProductIds.contains(maGiamSp.getSanPham().getId())) {
						appliedProductIds.add(maGiamSp.getSanPham().getId()); // Đánh dấu sản phẩm này đã được áp dụng
																				// voucher
						validVoucher = true; // Cho phép áp dụng voucher cho sản phẩm
					} else {
						validVoucher = false; // Nếu đã có voucher cho sản phẩm, không cho phép chọn thêm
					}
				}
			}

			// Cập nhật trạng thái voucher dựa trên điều kiện đã kiểm tra
			if (validVoucher) {
				voucher.setChecked(false); // Chưa chọn
			} else {
				voucher.setChecked(null); // Không đủ điều kiện
			}
		}

		return vouchers;
	}

	@ModelAttribute("TotalMoney")
	public Double gettotal() {
		Double total = 0.0;
		for (GioHang gh : getList()) {
			total += gh.getSanPham().getGiaGiam() * gh.getSoLuong();
		}
		return total;
	}

	@ModelAttribute("Discount")
	public Double getDiscount() {
		Double discount = 0.0;

		for (MaGiamKh mgKH : getVoucher()) {
			if (mgKH.getMaGiamDh() != null && mgKH.getMaGiamSp() != null) {
				Double giamGia;
				if (mgKH.getMaGiamDh().getGiamGiaVND() != null) {
					giamGia = mgKH.getMaGiamDh().getGiamGiaVND();
				} else if (mgKH.getMaGiamDh().getPhanTramGG() != null) {
					giamGia = mgKH.getMaGiamDh().getPhanTramGG();
				} else {
					giamGia = 0.0;
				}

				// Tính tổng discount
				discount += giamGia * mgKH.getMaGiamSp().getGiaTri();
			}
			System.out.println("Discount Value: " + discount);
		}
		return discount;
	}

	@ModelAttribute("Discount")
	public MaGiamDh getMaGiamDh() {
		return new MaGiamDh(1, 100000.0, 0.0, 100000.0, 500000.0, "Giảm 100.000 đơn trên 500.000");
	}

	@GetMapping("/giohang")
	public String getIndexPage(Model model) {
		return "GioHang";
	}

}