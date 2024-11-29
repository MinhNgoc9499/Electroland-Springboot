package com.fpl.Electroland.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fpl.Electroland.dao.ChiTietDhDAO;
import com.fpl.Electroland.dao.CloudinaryService;
import com.fpl.Electroland.dao.DanhGiaDAO;
import com.fpl.Electroland.dao.GioHangDAO;
import com.fpl.Electroland.dao.HinhSpDAO;
import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.MauSpDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.dao.ThuocTinhDAO;
import com.fpl.Electroland.dao.ThuocTinhSpDAO;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.ChiTietDh;
import com.fpl.Electroland.model.DanhGia;
import com.fpl.Electroland.model.GioHang;
import com.fpl.Electroland.model.HinhSp;
import com.fpl.Electroland.model.SanPham;

import com.fpl.Electroland.model.ThuocTinh;
import com.fpl.Electroland.model.ThuocTinhSp;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestBody;


@Controller
public class detailController {

	@Autowired
	private CloudinaryService cloudinaryService;

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	SanPhamDAO spDAO;

	@Autowired
	HinhSpDAO hinhDAO;

	@Autowired
	MauSpDAO mauDAO;

	@Autowired
	ThuocTinhDAO ttDAO;

	@Autowired
	ThuocTinhSpDAO ttSPDAO;

	@Autowired
	DanhGiaDAO dgDAO;

	@Autowired
	Author author;

	@Autowired
	GioHangDAO ghDAO;

	@Autowired
	ChiTietDhDAO ctdhDAO;

	@GetMapping("/detail")
	public String getTrangCNCHU(@RequestParam("id") int id,
			@RequestParam(value = "filter", required = false) String filter, Model model) {
		getToDetailPage(model, id, filter);
		return "Detail";
	}

	@PostMapping("/detail/addToCart")
	public String getToCart(@RequestParam Map<String, String> allAtt) {

		if (author.getUserKhachHang() == null) { // kiểm tra đăng nhập chưa
			return "redirect:/dangky"; // chuyển thẳng về đăng nhập
		} else {
			int idPD = Integer.parseInt(allAtt.get("id"));
			addToCart(allAtt, idPD);
		}
		return "redirect:/giohang";
	}

	@PostMapping("/detail/danhgia")
	public String getComment(@Valid @ModelAttribute("danhGia") DanhGia dg, BindingResult rs,
			@RequestParam("file") MultipartFile file,Model model) throws IOException {
		dg.setKhachHang(author.getUserKhachHang());
		if (file.isEmpty()) { 
			dgDAO.save(dg);
		} else {
			String url = cloudinaryService.uploadMultipleFile(file);
			dg.setImg(url);
			dgDAO.save(dg);
		}
		return "redirect:/detail?id=1";
	}

	void getToDetailPage(Model model, int id, String filter) {
		Optional<SanPham> sp = spDAO.findById(id);
		DanhGia danhGia = new DanhGia();
		danhGia.setSanPham(sp.get());
		if (sp.isPresent()) {
			List<HinhSp> listHinh = hinhDAO.findBySanPham(sp.get());
			List<String> listColor = mauDAO.findTenMauBySanPham(sp.get());
			List<ThuocTinhSp> listTTSP = ttSPDAO.findBySanPham(sp.get());
			List<DanhGia> listDG = dgDAO.findBySanPhamId(id);
			List<SanPham> listSPREC = spDAO.findByLoaiSanPham(sp.get().getLoaiSanPham());
			List<ChiTietDh> listCT = ctdhDAO.findSanPhamDaMuaByKhachHang(author.getUserKhachHang(), sp.get());

			Map<String, List<ThuocTinh>> mapTT = new HashMap<>();
			Map<String, List<ThuocTinh>> tableTT = new HashMap<>();
			listTTSP.forEach(ttsp -> {
				List<ThuocTinh> listTT = ttDAO.findByThuocTinhSp(ttsp);
				if (listTT.size() > 1) {
					mapTT.put(ttsp.getTen(), listTT); // mapTT dùng để chứa các thuộc tính có trên 2 giá trị
				}
				tableTT.put(ttsp.getTen(), listTT); // tableTT dùng để chứa tất cả các thuộc tính không phân biệt
			});
			// lấy điểm trung bình và số lần đánh giá
			double avgScore = listDG.stream().mapToInt(DanhGia::getDiem).average().orElse(0.0);
			int totalRatings = listDG.size();

			// filter đánh giá
			List<DanhGia> filteredReviews = listDG;
			if (filter != null) {
				switch (filter) {
					case "hasImage":
						filteredReviews = listDG.stream().filter(dg -> dg.getImg() != null)
								.collect(Collectors.toList());
						break;
					case "hasComment":
						filteredReviews = listDG.stream()
								.filter(dg -> dg.getNoiDung() != null && !dg.getNoiDung().isEmpty())
								.collect(Collectors.toList());
						break;
					case "all":
						filteredReviews = listDG;
						break;
					default:
						int ratingFilter = Integer.parseInt(filter);
						filteredReviews = listDG.stream().filter(dg -> dg.getDiem() == ratingFilter)
								.collect(Collectors.toList());
						break;
				}
			}
			// đếm số lần đánh giá
			Map<Integer, Long> ratingCounts = listDG.stream()
					.collect(Collectors.groupingBy(DanhGia::getDiem, Collectors.counting()));

			//Khóa bình luận 
			Boolean isDisabled = true ;
			if(listCT.size()>=1){
				isDisabled = false;
			}
			System.out.println(listCT.size() + " " + isDisabled);
			model.addAttribute("product", sp.get());
			model.addAttribute("pictures", listHinh);
			model.addAttribute("colors", listColor);
			model.addAttribute("selectAtt", mapTT);
			model.addAttribute("listTT", tableTT);
			model.addAttribute("reviews", filteredReviews);
			model.addAttribute("avgScore", avgScore);
			model.addAttribute("totalRatings", totalRatings);
			model.addAttribute("ratingCounts", ratingCounts);
			model.addAttribute("recommendedProducts", listSPREC);
			model.addAttribute("danhGia", danhGia);
			model.addAttribute("isDisabled", isDisabled);
		}
	}

	void addToCart(Map<String, String> allAtt, int id) {
		// xử lý khi đã đăng nhập
		allAtt.remove("id");// xóa key id để không truyển id vào moTa
		Optional<SanPham> product = spDAO.findById(id);
		if (product.isPresent()) {
			Optional<GioHang> cart = ghDAO.findBySanPhamAndMoTaAndKhachHang(product.get(), allAtt.toString(),
					author.getUserKhachHang());
			if (cart.isPresent()) {
				cart.get().setSoLuong(cart.get().getSoLuong() + 1);
				ghDAO.save(cart.get());
			} else {
				GioHang newCart = new GioHang(0, 1, product.get(), allAtt.toString(), author.getUserKhachHang(),
						true);
				ghDAO.save(newCart);
				System.out.println(ghDAO.toString());
			}
		}
	}

}