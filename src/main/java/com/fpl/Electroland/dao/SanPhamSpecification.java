package com.fpl.Electroland.dao;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.fpl.Electroland.model.Mau;
import com.fpl.Electroland.model.MauSp;
import com.fpl.Electroland.model.SanPham;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;

public class SanPhamSpecification {

    public static Specification<SanPham> hasLoaiSanPham(String loaiSanPhamId) {
        return (root, query, criteriaBuilder) -> {
            if (loaiSanPhamId != null) {
                try {
                    Integer id = Integer.parseInt(loaiSanPhamId);
                    return criteriaBuilder.equal(root.get("loaiSanPham").get("id"), id);
                } catch (NumberFormatException e) {
                    return criteriaBuilder.conjunction();
                }
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<SanPham> orderByPrice(String sortOrder) {
        return (root, query, criteriaBuilder) -> {
            if ("asc".equals(sortOrder)) {
                query.orderBy(criteriaBuilder.asc(root.get("giaGiam")));
            } else if ("desc".equals(sortOrder)) {
                query.orderBy(criteriaBuilder.desc(root.get("giaGiam")));
            } else {
                query.orderBy(criteriaBuilder.asc(root.get("giaGiam")));
            }
            return query.getRestriction();
        };
    }  
    

    public static Specification<SanPham> hasMinPrice(String minPrice) {
        return (root, query, criteriaBuilder) -> {
            if (minPrice != null && !minPrice.isEmpty()) {
                try {
                    Integer minPriceInt = Integer.parseInt(minPrice);
                    return criteriaBuilder.greaterThanOrEqualTo(root.get("giaGiam"), minPriceInt);
                } catch (NumberFormatException e) {
                    return criteriaBuilder.conjunction();
                }
            }
            return criteriaBuilder.conjunction();
        };
    }
    
    public static Specification<SanPham> hasMaxPrice(String maxPrice) {
        return (root, query, criteriaBuilder) -> {
            if (maxPrice != null && !maxPrice.isEmpty()) {
                try {
                    Integer maxPriceInt = Integer.parseInt(maxPrice);
                    return criteriaBuilder.lessThanOrEqualTo(root.get("giaGiam"), maxPriceInt);
                } catch (NumberFormatException e) {
                    return criteriaBuilder.conjunction();
                }
            }
            return criteriaBuilder.conjunction();
        };
    }
    
    public static Specification<SanPham> hasNhaCungCap(List<String> nhaCungCapIds) {
        return (root, query, criteriaBuilder) -> {
            if (nhaCungCapIds != null && !nhaCungCapIds.isEmpty()) {
                return root.get("nhaCungCap").get("id").in(nhaCungCapIds);
            }
            return criteriaBuilder.conjunction();
        };
    }

    public static Specification<SanPham> hasSearchKeyword(String search) {
        return (root, query, criteriaBuilder) -> {
            if (search != null) {
                return criteriaBuilder.like(root.get("tenSP"), "%" + search + "%");
            }
            return criteriaBuilder.conjunction();
        };  
    }

    public static Specification<SanPham> hashMauSp(List<String> mauIds) {
        return (root, query, criteriaBuilder) -> {
            if (mauIds != null && !mauIds.isEmpty()) {
                Join<SanPham, MauSp> mauSpJoin = root.join("mauSp", JoinType.INNER);
                Join<MauSp, Mau> mauJoin = mauSpJoin.join("mau", JoinType.INNER);
                CriteriaBuilder.In<Object> inClause = criteriaBuilder.in(mauJoin.get("id"));
                for (String mauId : mauIds) {
                    inClause.value(mauId); 
                }
                return criteriaBuilder.and(inClause);
            }
            return criteriaBuilder.conjunction();
        };
    }   
}
