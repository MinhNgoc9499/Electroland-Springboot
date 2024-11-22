package com.fpl.Electroland.helper;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import com.fpl.Electroland.model.KhachHang;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class Author {
<<<<<<< HEAD
	private KhachHang userKhachHang = new KhachHang();
=======
	private KhachHang userKhachHang = null;
>>>>>>> 6441d502985b778ca713d515861a617c3765003a

	public KhachHang getUserKhachHang() {
		return userKhachHang;
	}

	public void setUserKhachHang(KhachHang userKhachHang) {
		this.userKhachHang = userKhachHang;
	}
}
