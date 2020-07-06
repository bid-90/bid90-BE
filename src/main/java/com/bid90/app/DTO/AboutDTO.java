package com.bid90.app.DTO;

import java.time.LocalDate;

public class AboutDTO {

	String name;
	LocalDate dateOfBirth;
	String ocupation;
	ImageDTO image;

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getdateOfBirth() {
		return dateOfBirth;
	}
	public void setdateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getOcupation() {
		return ocupation;
	}
	public void setOcupation(String ocupation) {
		this.ocupation = ocupation;
	}
	public void setImage(ImageDTO image) {
		this.image = image;
	}
	public ImageDTO getImage() {
		return image;
	}
	
	public AboutDTO(){
		super();
	}
	
	
}
