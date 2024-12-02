package com.fpl.Electroland.service;

import com.fpl.Electroland.dao.DonHangDAO;
import com.fpl.Electroland.model.DonHang;
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
}
