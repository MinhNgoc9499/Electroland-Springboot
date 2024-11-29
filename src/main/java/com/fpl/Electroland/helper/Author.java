package com.fpl.Electroland.helper;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.fpl.Electroland.model.KhachHang;
import com.fpl.Electroland.model.NhanVien;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Author {
	private KhachHang userKhachHang = null;

	public KhachHang getUserKhachHang() {
		return userKhachHang;
	}

	public void setUserKhachHang(KhachHang userKhachHang) {
		this.userKhachHang = userKhachHang;
	}


}
