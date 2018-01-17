package com.wis.demo.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.wis.demo.model.Brand;
import com.wis.demo.model.EngineType;
import com.wis.demo.model.Vehicle;
import com.wis.demo.util.Colors;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
@Rollback(false)
public class DataCreatorTest {

	@Autowired
	private BrandDao brandDao;

	@Autowired
	private VehicleDao vehicleDao;

	@Test
	public void testCreateData() {

		final List<String> brandNames = Lists.newArrayList("VW", "BMW", "Audi", "Honda", "Seat", "Fiat", "Opel");

		if (brandDao.count() == brandNames.size()) {
			// no need to create test data
			return;
		}
			
		final List<Brand> brands = new ArrayList<>();

		brandNames.forEach(brandName -> {
			brands.add(brandDao.save(new Brand(brandName)));
		});

		for (int i = 0; i < 10; i++) {

			final Vehicle vehicle = new Vehicle();

			vehicle.setBrand(brands.get(i % brands.size()));
			vehicle.setEngineType(EngineType.values()[i % EngineType.values().length]);

			final String exampleChassisNumber = getVehicleBrandNamePrefix(vehicle.getBrand()) + "-0000-" + i;

			vehicle.setChassisNumber(exampleChassisNumber);
			vehicle.setColor(Colors.values()[i % Colors.values().length].toString());
			vehicle.setDescription("some description " + i);
			vehicle.setDisplacement(1000 + 100 * i);
			vehicle.setFirstRegistrationDate(new Date());
			vehicle.setNumberOfDoors(i % 5 + 1);
			vehicle.setWeight(i + 10 * i);

			vehicleDao.save(vehicle);

		}
	}

	private String getVehicleBrandNamePrefix(Brand brand) {
		return brand.getName().substring(0, Math.min(brand.getName().length(), 3)).toUpperCase();
	}
}
