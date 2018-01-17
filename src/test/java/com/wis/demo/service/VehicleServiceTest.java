package com.wis.demo.service;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.wis.demo.dao.BrandDao;
import com.wis.demo.dao.DataCreatorTest;
import com.wis.demo.dao.VehicleDao;
import com.wis.demo.model.Brand;
import com.wis.demo.model.Vehicle;

@RunWith(SpringRunner.class)
@SpringBootTest
//@ComponentScan(includeFilters = {@Filter(VehicleService.class) })
public class VehicleServiceTest {

	@Autowired
	VehicleService vehicleService;
	
	@MockBean
	VehicleDao vehicleDao;
	
	@MockBean
	BrandDao brandDao;
	
	@MockBean
	DataCreatorTest exampleDataService;
	
	
	@Test
	public void testVehicleSave() {
		
		final Brand brand = new Brand("BMW");
		final Vehicle vehicle = new Vehicle();
		vehicle.setBrand(new Brand(1L));
		
		
		given(this.brandDao.getOne(1L)).willReturn(brand);
		
		vehicleService.saveVehicle(vehicle);

		verify(brandDao, times(1)).findOne(1L);
		verify(vehicleDao, times(1)).save(vehicle);
		
		
	}
	
	
}
