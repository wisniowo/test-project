package com.wis.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wis.demo.model.Brand;

public interface BrandDao extends JpaRepository<Brand, Long> {

	List<Brand> findAllByOrderByNameAsc();
	
}
