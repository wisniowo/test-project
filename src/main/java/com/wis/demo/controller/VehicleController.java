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
		System.out.println("----");
		System.out.println(errors.getErrorCount());
		if (errors.hasErrors()) {
			System.out.println(errors.getFieldErrors());
			model.addAttribute("readonly", false);
			return "vehicle";
		} else {
			vehicleService.saveVehicle(vehicle);
			userMessage.setMessage(messages.getMessage("user.confirmation.saved", null, locale));
		}
		return "redirect:/vehicles";
	}

	/*
	 * @RequestMapping("/user/{id}") public String user(@PathVariable Long id, Model
	 * model) { model.addAttribute("user", helloService.findById(id)); return
	 * "user"; }
	 * 
	 * @RequestMapping("/users-save") public String usersSave(@Valid UsersForm
	 * usersForm, Errors errors, BindingResult bindingResult) {
	 * System.out.println("----"); usersForm.getUsers().stream().forEach(user ->
	 * System.out.println(user.getEmail()));
	 * 
	 * System.out.println(errors.getErrorCount()); if (errors.hasErrors()) {
	 * System.out.println(errors.getFieldErrors()); return "users"; } else {
	 * helloService.updateUsers(usersForm.getUsers()); } return "redirect:/users"; }
	 * 
	 * @RequestMapping("/users/remove/{id}") public String usersSave(@PathVariable
	 * Long id) { helloService.removeUser(id); return "redirect:/users"; }
	 * 
	 * @RequestMapping("/users/cart-add/{id}") public String
	 * addToShoppingCart(@PathVariable Long id) {
	 * helloService.addUserToShoppingCart(id); return "redirect:/users"; }
	 * 
	 * @RequestMapping("/users/cart-remove/{id}") public String
	 * removeFromShoppingCart(@PathVariable Long id) {
	 * helloService.removeUserFromShoppingCart(id); return "redirect:/users"; }
	 * 
	 * @ModelAttribute("allRoles") public List<Role> allRoles() { return
	 * Arrays.asList(Role.values()); }
	 * 
	 * @ModelAttribute("shoppingCart") public ShoppingCart shoppingCart() { return
	 * shoppingCart; }
	 */

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
