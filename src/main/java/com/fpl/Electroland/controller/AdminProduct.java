package com.fpl.Electroland.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.angus.mail.handlers.multipart_mixed;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.fpl.Electroland.dao.HinhSpDAO;
import com.fpl.Electroland.dao.LoaiSanPhamDAO;
import com.fpl.Electroland.dao.MauDAO;
import com.fpl.Electroland.dao.MauSpDAO;
import com.fpl.Electroland.dao.NhaCungCapDAO;
import com.fpl.Electroland.dao.SanPhamDAO;
import com.fpl.Electroland.dao.ThuocTinhDAO;
import com.fpl.Electroland.dao.ThuocTinhSpDAO;
import com.fpl.Electroland.model.HinhSp;
import com.fpl.Electroland.model.LoaiSanPham;
import com.fpl.Electroland.model.Mau;
import com.fpl.Electroland.model.MauSp;
import com.fpl.Electroland.model.NhaCungCap;
import com.fpl.Electroland.model.SanPham;
import com.fpl.Electroland.model.ThuocTinh;
import com.fpl.Electroland.model.ThuocTinhSp;

import jakarta.servlet.ServletContext;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequestMapping("/admin")
public class AdminProduct {
    @Autowired
    SanPhamDAO spDao;

    @Autowired
    LoaiSanPhamDAO loaiDAO;

    @Autowired
    NhaCungCapDAO nhaCCDAO;

    @Autowired
    MauDAO mauDAO;

    @Autowired
    ThuocTinhSpDAO ttSPDAO;

    @Autowired
    ThuocTinhDAO ttDAO;

    @Autowired
    MauSpDAO mauSpDAO;

    @Autowired
    ServletContext app;

    @Autowired
    HinhSpDAO hinhSpDAO;

    @ModelAttribute("listSP")
    public List<SanPham> getSP() {
        return spDao.findAll();
    }

    @ModelAttribute("listLoaiSP")
    public List<LoaiSanPham> getloaiSP() {
        return loaiDAO.findAll();
    }

    @ModelAttribute("listNhaCC")
    public List<NhaCungCap> getNhaCC() {
        return nhaCCDAO.findAll();
    }

    @ModelAttribute("listMau")
    public List<Mau> getMau() {
        return mauDAO.findAll();
    }

    // @ModelAttribute("product")
    // public SanPham sp(){
    // return spDao.findById(1).get();
    // }

    @GetMapping("/product")
    public String adminProduct(@RequestParam(value = "categoryId", required = false) Integer categoryId,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "sortByPrice", required = false) String sortByPrice,
            @RequestParam(value = "trangThai", required = false) Boolean trangThai, Model model) {
        if (categoryId != null) {
            List<SanPham> listSP = spDao.findByIdLoaiSP(categoryId);
            model.addAttribute("listSP", listSP);
        }
        if (name != null) {
            List<SanPham> listSP = spDao.findByTenSPContaining(name);
            model.addAttribute("listSP", listSP);
        }

        if (sortByPrice != null) {
            List<SanPham> listSP = spDao.findBySortByPrice(sortByPrice);
            model.addAttribute("listSP", listSP);
        }

        if (trangThai != null) {
            List<SanPham> listSP = spDao.findByTrangThai(trangThai);
            model.addAttribute("listSP", listSP);
        }
        return "productADM";
    }

    @GetMapping("/product-detail")
    public String adminProductDetail(@RequestParam(value = "id", required = false) Integer id, Model model) {
        if (id == null) {

            List<Mau> listMau = new ArrayList<>();
            listMau.add(new Mau(0, "Default"));

            List<ThuocTinhSp> listTTSP = new ArrayList<>();
            listTTSP.add(new ThuocTinhSp());

            Map<String, List<ThuocTinh>> tableTT = new HashMap<>();
            tableTT.put("Tên thuộc tính mẫu", Collections.singletonList(new ThuocTinh(0, null, "Giá trị mẫu", null)));
            model.addAttribute("listTT", tableTT);
            model.addAttribute("listMauSP", listMau);
            model.addAttribute("product", new SanPham());
            return "productDetailADM";

        }
        Optional<SanPham> sp = spDao.findById(id);
        List<Mau> listColor = mauDAO.findMauBySanPham(sp.get());
        List<ThuocTinhSp> listTTSP = ttSPDAO.findBySanPham(sp.get());
        Map<String, List<ThuocTinh>> tableTT = new HashMap<>();
        listTTSP.forEach(ttsp -> {
            List<ThuocTinh> listTT = ttDAO.findByThuocTinhSp(ttsp);
            tableTT.put(ttsp.getTen(), listTT);
        });
        model.addAttribute("product", sp.get());
        model.addAttribute("listMauSP", listColor);
        model.addAttribute("listTT", tableTT);
        return "productDetailADM";
    }

    @PostMapping("/product-detail")
    public String AddProduct(@ModelAttribute("product") SanPham product,
            @RequestParam("fileUpload") MultipartFile file,
            @RequestParam("fileHinhSP") List<MultipartFile> fileHinhSP,
            @RequestParam("color") String[] mausps,
            @RequestParam("tenTT") String[] tenTT,
            @RequestParam("giaTriTT") String[] gtriTT) throws IllegalStateException, IOException {
        product.setHinhDaiDien(file.getResource().getFilename());
        saveFile(file);
        product = spDao.save(product);
        for (MultipartFile img : fileHinhSP) {
            HinhSp hinhSp = new HinhSp();
            hinhSp.setLink(img.getResource().getFilename());
            hinhSp.setSanPham(product);
            hinhSpDAO.save(hinhSp);
            try {
                saveFile(img);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
        }
        ;

        for (String mausp : mausps) {
            MauSp mauSanPham = new MauSp();
            mauSanPham.setMau(mauDAO.findById(Integer.parseInt(mausp)).get());
            mauSanPham.setSanPham(product);
            if (!mauSpDAO.findByMauAndSanPham(mauDAO.findById(Integer.parseInt(mausp)).get(), product).isPresent())
                mauSpDAO.save(mauSanPham);
        }
        for (int i = 0; i < tenTT.length; i++) {
            Optional<ThuocTinhSp> thuoctinhsp = ttSPDAO.findBySanPhamAndTenLike(product, tenTT[i]);
            if (!thuoctinhsp.isPresent()) {
                ThuocTinhSp newtt = new ThuocTinhSp();
                newtt.setSanPham(product);
                newtt.setTen(tenTT[i]);
                thuoctinhsp = ttSPDAO.findById(ttSPDAO.save(newtt).getId());
            }
            ThuocTinh thuocTinh = new ThuocTinh();
            thuocTinh.setTenTT(gtriTT[i]);
            thuocTinh.setThuocTinhSp(thuoctinhsp.get());
            ttDAO.save(thuocTinh);
        }

        return "redirect:/admin/product-detail?id=" + product.getId();
    }

    public String saveFile(MultipartFile file) throws IllegalStateException, IOException {

        String uploadDir = app.getRealPath("/IMG");
        File uploadFolder = new File(uploadDir);

        if (!uploadFolder.exists()) {
            uploadFolder.mkdirs();
        }

        String fileName = file.getOriginalFilename();

        File destinationFile = new File(uploadFolder, fileName);

        file.transferTo(destinationFile);

        return fileName;
    }

}
