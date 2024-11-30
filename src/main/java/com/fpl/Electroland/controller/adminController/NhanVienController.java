package com.fpl.Electroland.controller.adminController;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fpl.Electroland.dao.NhanVienDAO;
import com.fpl.Electroland.model.NhanVien;

@Controller
@RequestMapping("/admin")
public class NhanVienController {

    @Autowired
    NhanVienDAO nhanVienDAO;

    @ModelAttribute("ListNV")
    public List<NhanVien> getListNV(@RequestParam(name = "id", required = false) String id) {
        if (id != null) {
            List<NhanVien> list = nhanVienDAO.findByIdLike(id);
            if (!list.isEmpty() && list != null) {
                return list;
            }
        }
        return nhanVienDAO.findAll();
    }

    @ModelAttribute("nhanVien")
    public NhanVien getNhanVien(@RequestParam(name = "id", required = false, defaultValue = "0") int id) {
        if (id != 0) {
            Optional<NhanVien> nv = nhanVienDAO.findById(id);
            if (nv.isPresent())
                return nv.get();
        }
        return new NhanVien();
    }

    @GetMapping("/Employes")
    public String Employes() {
        return "employessADM";
    }

    @GetMapping("/Employes/Details")
    public String adimEmployesDetail() {
        return "EmployessDetailADM";
    }

    @PostMapping("/Employes/Add")
    public String adimEmployesAdd(@ModelAttribute("nhanVien") NhanVien nv) {
        nhanVienDAO.save(nv);
        return "EmployessDetailADM";
    }

    @GetMapping("/Employes/Delete")
    public String adimEmployesDelete(@RequestParam("id") int id) {
        nhanVienDAO.deleteById(id);
        return "redirect:/admin/Employes";
    }

    @PostMapping("/Employes/Update")
    public String adimEmployesUpdate(@ModelAttribute("nhanVien") NhanVien nv) {
        nhanVienDAO.save(nv);
        return "EmployessDetailADM";
    }
}