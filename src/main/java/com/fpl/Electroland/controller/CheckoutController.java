package com.fpl.Electroland.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.fpl.Electroland.dao.ChiTietDhDAO;
import com.fpl.Electroland.dao.DiaChiDAO;
import com.fpl.Electroland.dao.DonHangDAO;
import com.fpl.Electroland.dao.GioHangDAO;
import com.fpl.Electroland.dao.GioHangThuocTinhDAO;
import com.fpl.Electroland.dao.KhachHangDAO;
import com.fpl.Electroland.dao.MaGiamKhDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.ChiTietDh;
import com.fpl.Electroland.model.DiaChi;
import com.fpl.Electroland.model.DonHang;
import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.GioHangThuocTinh;
import com.fpl.Electroland.model.MaGiamDh;
import com.fpl.Electroland.model.MaGiamKh;

import jakarta.validation.Valid;

@Controller
public class CheckoutController {

	@Autowired
	KhachHangDAO khachHangDAO;

	@Autowired
	DonHangDAO donHangDAO;

	@Autowired
	ChiTietDhDAO chiTietDhDAO;

	@Autowired
	GioHangDAO gioHangDAO;

	@Autowired
	Author author;

	@Autowired
	MaGiamKhDAO maGiamKhDAO;

	@Autowired
	DiaChiDAO diaChiDAO;
	@Autowired
	GioHangThuocTinhDAO gioHangThuocTinhDAO;

	@ModelAttribute("donHang")
	public DonHang getDonHang() {
		DiaChi diaChi = diaChiDAO.findByKhachHangAndMacDinhTrue(author.getUserKhachHang());
		if (diaChi == null)
			diaChi = new DiaChi();
		DonHang dh = new DonHang();
		dh.setKhachHang(author.getUserKhachHang());
		dh.setDiaChi(diaChi.getChiTiet());
		dh.setNguoiNhan(diaChi.getHoTenNN());
		dh.setSdt(diaChi.getSdtNN());
		dh.setTongTien(getTotal());
		dh.setTongGiam(getTotalDiscount());
		return dh;
	};

	@ModelAttribute("listDC")
	public List<DiaChi> getDC() {
		return diaChiDAO.findByKhachHang(author.getUserKhachHang());
	}

	@ModelAttribute("ListSelected")
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

	@ModelAttribute("TotalMoney")
	public Double getTotal() {
		Map<GioHang, Double> list = getList();
		Double total = 0.0;
		for (GioHang gh : list.keySet()) {
			total += list.get(gh) * gh.getSoLuong();
			System.out.println(total);
		}
		return total;
	}

	@ModelAttribute("TotalDiscount")
	public Double getTotalDiscount() {
		Double TotalMoney = getTotal();
		Double TotalDiscount = 0.0;
		List<MaGiamKh> list = getListMaGiamKH();
		for (MaGiamKh maGiamKh : list) {
			if (maGiamKh.getMaGiamDh() != null) {
				TotalDiscount += getDiscountDH(maGiamKh.getMaGiamDh(), TotalMoney);
			} else
				TotalDiscount += maGiamKh.getMaGiamSp().getGiaTri();
		}
		;
		return TotalDiscount;
	}

	@GetMapping("/thanhtoan")
	public String showRegistrationForm(Model model) {
		UUID uuid = UUID.randomUUID();
		model.addAttribute("key", uuid.toString().replaceAll("-", ""));
		return "checkout";
	}

	@PostMapping("/thanhtoan")
	public String Payment(@Valid @ModelAttribute("donHang") DonHang donhang, BindingResult rs, Model model) {
		if (donhang.getDiaChi() == null)
			rs.rejectValue("diaChi", "error.donHang", "Vui lòng chọn địa chỉ");
		System.out.println(donhang.getPhuongThucTT());
		if (donhang.getPhuongThucTT().equals(""))
			rs.rejectValue("phuongThucTT", "error.donHang", "Vui lòng chọn phương thức thanh toán");
		if (rs.hasErrors()) {
			return "checkout";
		}
		donhang.setMaGiamDh(maGiamKhDAO.getMGDHChecked(author.getUserKhachHang()));
		DonHang dh = donHangDAO.save(donhang);
		for (GioHang gioHang : getList().keySet()) {
			ChiTietDh CTDH = new ChiTietDh();
			CTDH.setDonHang(dh);
			CTDH.setGiaBan(gioHang.getSanPham().getGiaGiam() != null ? gioHang.getSanPham().getGiaGiam()
					: gioHang.getSanPham().getGiaBan());
			CTDH.setSoLuong(gioHang.getSoLuong());
			CTDH.setMoTa(gioHang.getMoTa());
			CTDH.setSanPham(gioHang.getSanPham());
			if (maGiamKhDAO.getMGSPChecked(author.getUserKhachHang(), gioHang.getSanPham()).size() > 0)
				CTDH.setMaGiamSp(
						maGiamKhDAO.getMGSPChecked(author.getUserKhachHang(), gioHang.getSanPham()).get(0)
								.getMaGiamSp());
			chiTietDhDAO.save(CTDH);
		}
		gioHangDAO.deleteByKhachHangAndCheckedTrue(author.getUserKhachHang());
		maGiamKhDAO.deleteByKhachHangAndCheckedTrue(author.getUserKhachHang());
		return "redirect:/order_detail?id=" + donhang.getId();
	}

	public List<MaGiamKh> getListMaGiamKH() {
		return maGiamKhDAO.findByKhachHangAndChecked(author.getUserKhachHang(), true);
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
