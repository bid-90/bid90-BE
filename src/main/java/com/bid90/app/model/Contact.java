package com.bid90.app.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;


@Entity
@Table(name = "contact")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String type;
	String value;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonBackReference
	About about;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public About getAbout() {
		return about;
	}
	public void setAbout(About about) {
		this.about = about;
		about = this.about;
	}
	public void removeAbout(About about){
		if(this.about == about) {
			this.about = null;
			about = null;
		}
		
	}
	public Contact() {
		super();
	}
	
	
	

}
