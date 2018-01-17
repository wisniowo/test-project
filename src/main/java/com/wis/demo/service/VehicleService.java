package com.wis.demo.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wis.demo.dao.BrandDao;
import com.wis.demo.dao.VehicleDao;
import com.wis.demo.model.Brand;
import com.wis.demo.model.Vehicle;

@Service
public class VehicleService {

	@Autowired
	private BrandDao brandDao;

	@Autowired
	private VehicleDao vehicleDao;

	public List<Vehicle> getAllVehicles() {
		return vehicleDao.findAllFetchBrandByOrderByIdDesc();
	}

	public List<Brand> getAllBrands() {
		return brandDao.findAllByOrderByNameAsc();
	}

	public Vehicle findById(final Long id) {
		return vehicleDao.findOne(id);
	}

	@Transactional
	public void saveVehicle(Vehicle vehicle) {
		final Brand brand = brandDao.findOne(vehicle.getBrand().getId());
		vehicle.setBrand(brand);
		vehicleDao.save(vehicle);
	}

	@Transactional
	public void deleteVehicle(Long id) {
		vehicleDao.delete(id);
	}

	public void removeVehicle(Long id) {
		vehicleDao.delete(id);
	}

}
