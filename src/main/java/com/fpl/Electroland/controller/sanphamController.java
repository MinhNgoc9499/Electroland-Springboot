package com.fpl.Electroland.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpl.Electroland.dao.LoaiKhachHangDAO;
import com.fpl.Electroland.dao.LoaiSanPhamDAO;
import com.fpl.Electroland.dao.MauDAO;
import com.fpl.Electroland.dao.NhaCungCapDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.dao.SanPhamSpecification;
import com.fpl.Electroland.helper.Author;
import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.Mau;
import com.fpl.Electroland.model.NhaCungCap;
import com.fpl.Electroland.model.SanPham;

@Controller
public class sanphamController {

	@Autowired
	LoaiKhachHangDAO dao;

	@Autowired
	Author author;

	@Autowired
	LoaiSanPhamDAO loaiSPDAO;

	@Autowired
	SanPhamDAO sanPhamDAO;

	@Autowired
	NhaCungCapDAO nccDAO;
	
	@Autowired
	MauDAO mauDAO;

	@ModelAttribute("listMauSP")
	public List<Mau> listMauSP(){
		List<Mau> listMauSp = mauDAO.findAll();
		return listMauSp;
	}

	@ModelAttribute("listLoaiSP")
    public List<LoaiSanPham> listLoaiSP(){
        List<LoaiSanPham> listLoaiSP = loaiSPDAO.findAll();
        return listLoaiSP;
    }

	@ModelAttribute("listNhaCC")
	public List<NhaCungCap> listNhaCungCap(){
		List<NhaCungCap> listNhaCungCap = nccDAO.findAll();
		return listNhaCungCap;
	}

	@GetMapping("/sanpham")
	public String getProducts(@RequestParam(defaultValue = "0") int page, 
							@RequestParam(defaultValue = "12") int size,
							@RequestParam(name="search", required = false) String search,
							@RequestParam(value = "loaiSanPham", required = false) String loaiSanPhamId,
							@RequestParam(value = "minPrice", required = false) String minPrice,
							@RequestParam(value = "maxPrice", required = false) String maxPrice,
							@RequestParam(value = "nhaCungCap", required = false) List<String> nhaCungCapIds,
							@RequestParam(value = "mauIds", required = false) List<String> mauIds,
							@RequestParam(value = "sortOrder", required = false) String sortOrder,
							Model model) {
		Specification<SanPham> spec = Specification.where(SanPhamSpecification.hasSearchKeyword(search))
												.and(SanPhamSpecification.hasMinPrice(minPrice))
												.and(SanPhamSpecification.hasMaxPrice(maxPrice));

		if (nhaCungCapIds != null && !nhaCungCapIds.isEmpty()) {
			spec = spec.and(SanPhamSpecification.hasNhaCungCap(nhaCungCapIds));
		}

		if (mauIds != null && !mauIds.isEmpty()) {
			spec = spec.and(SanPhamSpecification.hashMauSp(mauIds));
		}

		if (loaiSanPhamId != null) {
			spec = spec.and(SanPhamSpecification.hasLoaiSanPham(loaiSanPhamId));
		}

		if (sortOrder != null && !sortOrder.isEmpty()) {
			spec = spec.and(SanPhamSpecification.orderByPrice(sortOrder));
		}	
		
		Pageable pageable = PageRequest.of(page, size);
    	Page<SanPham> sanPhamList = sanPhamDAO.findAll(spec, pageable);

		int totalPages = sanPhamList.getTotalPages();
		int startPage = Math.max(0, page - 2);  // Hiển thị tối thiểu 2 trang trước
		int endPage = Math.min(totalPages - 1, page + 2);
		
		long count = sanPhamList.getTotalElements();
		model.addAttribute("search", search);
		model.addAttribute("count", count);
		model.addAttribute("product", sanPhamList);
		model.addAttribute("totalPages", sanPhamList.getTotalPages());
    	model.addAttribute("currentPage", page);
		model.addAttribute("startPage", startPage);
    	model.addAttribute("endPage", endPage);
    	return "SanPham";
	}
}
