package com.wis.demo.controller;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.wis.demo.model.Brand;
import com.wis.demo.model.EngineType;
import com.wis.demo.model.Vehicle;
import com.wis.demo.service.VehicleService;

@Controller
public class VehicleController {

	@Autowired
	MessageSource messages;

	@Autowired
	VehicleService vehicleService;

	@Autowired
	UserMessageBean userMessage;

	@RequestMapping({ "/", "/vehicles" })
	public String vehicles(Model model) {
		model.addAttribute("vehicles", vehicleService.getAllVehicles());
		return "vehicles";
	}

	@RequestMapping("/vehicle/add")
	public String vehicleAdd(Model model) {
		model.addAttribute("vehicle", new Vehicle());
		model.addAttribute("readonly", false);
		return "vehicle";
	}

	@RequestMapping("/vehicle/edit/{id}")
	public String vehicleEdit(@PathVariable Long id, Model model) {
		model.addAttribute("vehicle", vehicleService.findById(id));
		model.addAttribute("readonly", false);
		return "vehicle";
	}

	@RequestMapping("/vehicle/view/{id}")
	public String vehicleView(@PathVariable Long id, Model model) {
		model.addAttribute("vehicle", vehicleService.findById(id));
		model.addAttribute("readonly", true);
		return "vehicle";
	}

	@RequestMapping("/vehicle/delete/{id}")
	public String vehicleDelete(@PathVariable Long id, Locale locale) {
		vehicleService.deleteVehicle(id);
		userMessage.setMessage(messages.getMessage("user.confirmation.deleted", null, locale));
		return "redirect:/vehicles";
	}

	@RequestMapping("/vehicle/save")
	public String vehicleSave(@Valid Vehicle vehicle, Errors errors, BindingResult bindingResult, Model model, Locale locale) {
		if (errors.hasErrors()) {
			model.addAttribute("readonly", false);
			return "vehicle";
		} else {
			vehicleService.saveVehicle(vehicle);
			userMessage.setMessage(messages.getMessage("user.confirmation.saved", null, locale));
		}
		return "redirect:/vehicles";
	}

	@ModelAttribute("brands")
	public List<Brand> getBrands() {
		return vehicleService.getAllBrands();
	}

	@ModelAttribute("engineTypes")
	public EngineType[] getEngineType() {
		return EngineType.values();
	}

	@ModelAttribute("userMessage")
	public String getUserMessage() {
		return userMessage.getAndClearMessage();
	}

	@ModelAttribute("loggedInUser")
	public String getLoggedInUser(Principal principal) {
		return principal != null ? principal.getName() : null;
	}

}
