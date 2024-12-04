package com.fpl.Electroland.service;

import com.fpl.Electroland.dao.ChiTietDhDAO;
import com.fpl.Electroland.dao.DonHangDAO;
import com.fpl.Electroland.dto.OrderDtoRequest;
import com.fpl.Electroland.model.ChiTietDh;
import com.fpl.Electroland.model.DonHang;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DonHangService {

    @Autowired
    private DonHangDAO donHangDAO;

    @Autowired
    private ChiTietDhDAO chiTietDhDAO;

    public Page<DonHang> pageDonHang(String searchOrderId, String searchCustomerName, String searchPhoneNumber,
        String searchAddress, String paymentMethod, String status, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        Boolean isSearchOrderId = StringUtils.isNotBlank(searchOrderId);
        Boolean isSearchCustomerName = StringUtils.isNotBlank(searchCustomerName);
        Boolean isSearchPhoneNumber = StringUtils.isNotBlank(searchPhoneNumber);
        Boolean isSearchAddress = StringUtils.isNotBlank(searchAddress);
        Boolean isPaymentMethod = StringUtils.isNotBlank(paymentMethod);
        Boolean isStatus = StringUtils.isNotBlank(status);

        if (isSearchOrderId || isSearchCustomerName || isSearchPhoneNumber || isSearchAddress || isPaymentMethod || isStatus) {
            return donHangDAO.pageDonHang(searchOrderId, searchCustomerName, searchPhoneNumber, searchAddress,
                paymentMethod, status, pageable);
        } else {
            return donHangDAO.findAll(pageable);
        }

    }

    @Transactional
    public void updateDonHang(OrderDtoRequest orderDtoRequest) {

        DonHang donHang = donHangDAO.findById(orderDtoRequest.getId());
        donHang.setTrangThai(orderDtoRequest.getTrangThai());

        List<ChiTietDh> chiTietDhList = new ArrayList<>();
        for (OrderDtoRequest.ProductDTO productDTO : orderDtoRequest.getChiTietDhs()) {
            ChiTietDh chiTietDh = chiTietDhDAO.findChiTietDhByDonHangIdAndSanPhamId(donHang.getId(), productDTO.getId());
            chiTietDh.setSoLuong(productDTO.getSoLuong());
            chiTietDhList.add(chiTietDh);
        }
        chiTietDhDAO.saveAll(chiTietDhList);
        donHangDAO.save(donHang);
    }
}
