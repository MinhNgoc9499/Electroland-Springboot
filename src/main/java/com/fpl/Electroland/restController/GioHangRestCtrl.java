package com.fpl.Electroland.restController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        GioHang gh =  gioHangDAO.findById(id).get(); 
        if(gh.isChecked() == true){
            gh.setChecked(false); 
        }
        else{
            gh.setChecked(true);
        }
        gioHangDAO.save(gh); 
    }
}