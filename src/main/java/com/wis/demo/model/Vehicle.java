package com.wis.demo.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Vehicle extends AbstractModel {

	@NotNull
	@ManyToOne(fetch = FetchType.EAGER)
	@Fetch(FetchMode.JOIN)
	private Brand brand;

	@NotNull
	private EngineType engineType;

	@NotNull
	@Digits(integer = 20, fraction = 0)
	@Min(0)
	private Integer displacement;

	@NotNull
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date firstRegistrationDate;

	@NotNull
	@Digits(integer = 10, fraction = 0)
	@Min(1)
	private Integer numberOfDoors;

	@NotBlank
	@Length(max = 100)
	private String color;

	@NotNull
	@Digits(integer = 20, fraction = 0)
	@Min(0)
	private Integer weight;

	@NotBlank
	@Length(max = 100)
	private String chassisNumber;

	@Length(max = 5000)
	@Lob
	private String description;

	public Brand getBrand() {
		return brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public EngineType getEngineType() {
		return engineType;
	}

	public void setEngineType(EngineType engineType) {
		this.engineType = engineType;
	}

	public Integer getDisplacement() {
		return displacement;
	}

	public void setDisplacement(Integer displacement) {
		this.displacement = displacement;
	}

	public Date getFirstRegistrationDate() {
		return firstRegistrationDate;
	}

	public void setFirstRegistrationDate(Date firstRegistrationDate) {
		this.firstRegistrationDate = firstRegistrationDate;
	}

	public Integer getNumberOfDoors() {
		return numberOfDoors;
	}

	public void setNumberOfDoors(Integer numberOfDoors) {
		this.numberOfDoors = numberOfDoors;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getChassisNumber() {
		return chassisNumber;
	}

	public void setChassisNumber(String chassisNumber) {
		this.chassisNumber = chassisNumber;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
