package com.fpl.Electroland.dao;

import com.fpl.Electroland.model.NhanVien;
import com.fpl.Electroland.model.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NhanVienDetailService implements UserDetailsService {

    private final NhanVienDAO nhanvienDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<NhanVien> userInfo = nhanvienDao.findByEmail(username);
        if (userInfo.isPresent()) {
            return new User( userInfo.get().getHoTen(),userInfo.get().getEmail(), userInfo.get().getMatKhau(), userInfo.get().getChucVu());
        }
        throw new UsernameNotFoundException(username);
    }

    public Optional<NhanVien> findByEmail(String email) {
        Optional<NhanVien> nv = nhanvienDao.findByEmail(email);
        if (nv.isEmpty()) {
            System.out.println("Không thấy nhân Viên: "+ email);
        }
        return nv;
    }

}
