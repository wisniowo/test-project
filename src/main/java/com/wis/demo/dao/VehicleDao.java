package com.wis.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.wis.demo.model.Vehicle;

public interface VehicleDao extends JpaRepository<Vehicle, Long> {
	
	@Query("from Vehicle v join fetch v.brand order by v.id desc")
	public List<Vehicle> findAllFetchBrandByOrderByIdDesc();
	
}
