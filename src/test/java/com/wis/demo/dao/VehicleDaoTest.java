package com.wis.demo.dao;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import com.wis.demo.model.Brand;
import com.wis.demo.model.EngineType;
import com.wis.demo.model.Vehicle;

@RunWith(SpringRunner.class)
@SpringBootTest
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Transactional
@Rollback(true)
public class VehicleDaoTest {

	@Autowired
	VehicleDao vehicleDao;

	@Autowired
	private TestEntityManager entityManager;

	@Test
	public void testVehicleSave() {

		long count = vehicleDao.count();

		Brand brand = new Brand("BMW");

		brand = entityManager.persistAndFlush(brand);

		Vehicle vehicle = new Vehicle();

		vehicle.setBrand(brand);
		vehicle.setChassisNumber("chassis");
		vehicle.setColor("color");
		vehicle.setDisplacement(1000);
		vehicle.setDescription("some description");
		vehicle.setEngineType(EngineType.diesel);
		vehicle.setFirstRegistrationDate(new Date());
		vehicle.setNumberOfDoors(2);
		vehicle.setWeight(2000);

		Long id = vehicleDao.save(vehicle).getId();

		entityManager.clear();

		vehicle = vehicleDao.getOne(id);

		assertEquals(count + 1, vehicleDao.count());
		assertEquals("BMW", vehicle.getBrand().getName());
		assertEquals(EngineType.diesel, vehicle.getEngineType());

	}

}
