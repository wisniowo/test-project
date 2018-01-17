package com.wis.demo.controller;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.List;

import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.wis.demo.model.Brand;
import com.wis.demo.model.EngineType;
import com.wis.demo.model.Vehicle;
import com.wis.demo.service.VehicleService;

@RunWith(SpringRunner.class)
@WebMvcTest(value = VehicleController.class, includeFilters = @Filter(classes = EnableWebSecurity.class))
public class VehicleControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private VehicleService vehicleService;

	@MockBean
	private UserMessageBean userMessageBean;
	
	private Vehicle testVehicle = createTestVehicle();
	
	@Test
	public void testVehicles() throws Exception {
		
		final List<Vehicle> vehicles = Lists.newArrayList(testVehicle);

		given(this.vehicleService.getAllVehicles()).willReturn(vehicles);
		
		this.mvc.perform(get("/vehicles").accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk())
			.andExpect(content().string(containsString("BMW")))
			.andExpect(content().string(containsString("Some chassis")));
	}

	@Test
	public void testVehicleAddUnauthorizedRedirectToLogin() throws Exception {
		this.mvc.perform(get("/vehicle/add").accept(MediaType.TEXT_PLAIN))
			.andExpect(redirectedUrlPattern("**/login"));
	}
	
	@Test
	public void testVehicleAddAuthorized() throws Exception {
		this.mvc.perform(get("/vehicle/add").with(user("admin").password("admin")).accept(MediaType.TEXT_PLAIN))
			.andExpect(status().isOk());
	}
	
	
	
	private Vehicle createTestVehicle() {
		final Vehicle vehicle = new Vehicle();
		vehicle.setBrand(new Brand());
		vehicle.getBrand().setName("BMW");
		vehicle.setEngineType(EngineType.diesel);
		vehicle.setChassisNumber("Some chassis");
		vehicle.setFirstRegistrationDate(new Date());
		vehicle.setDescription("some description");
		vehicle.setDisplacement(1000);
		return vehicle;
	}
	

}
