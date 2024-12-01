package com.fpl.Electroland.restController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import com.fpl.Electroland.model.MaGiamKh;

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
    }@PostMapping("/rest/giohang/updateVoucher/{id}")
    public Map<String, Object> updateVoucherFromCart(@PathVariable("id") int id) {
        Map<String, Object> response = new HashMap<>();
    
        try {
            MaGiamKh mgkh = mgkhDao.findById(id).orElseThrow(() -> new RuntimeException("Không tìm thấy voucher"));
    
            if (mgkh.getMaGiamDh() != null) {
                // Xử lý cho maGiamDh: Chỉ một voucher được chọn
                Optional<MaGiamKh> existingVoucher = mgkhDao.findByKhachHangAndMaGiamSpIsNullAndCheckedTrue(author.getUserKhachHang());
                if (existingVoucher.isPresent() && existingVoucher.get().getId() != id) {
                    MaGiamKh trungGian = existingVoucher.get();
                    trungGian.setChecked(false);
                    mgkhDao.save(trungGian);
                }
                // Cập nhật trạng thái voucher hiện tại
                mgkh.setChecked(!mgkh.getChecked());
            } else if (mgkh.getMaGiamSp() != null) {
                // Xử lý cho maGiamSp: Nhiều voucher có thể chọn cùng lúc
                mgkh.setChecked(!mgkh.getChecked());
            } else {
                throw new RuntimeException("Voucher không hợp lệ.");
            }
    
            mgkhDao.save(mgkh); // Lưu trạng thái mới vào DB
    
            // Phản hồi trạng thái mới
            response.put("success", true);
            response.put("checked", mgkh.getChecked());
            response.put("message", "Cập nhật voucher thành công.");
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }
    
}