package com.fpl.Electroland.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fpl.Electroland.model.Mau;

public interface MauDAO extends JpaRepository<Mau, Integer> {
    
}
