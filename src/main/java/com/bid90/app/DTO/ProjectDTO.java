package com.bid90.app.DTO;

import java.util.List;

import com.bid90.app.model.Link;

public class ProjectDTO {
	
	Long id;
	String title;
	String description;
	ImageDTO image;
	List<Link> links;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ImageDTO getImage() {
		return image;
	}
	public List<Link> getLinks() {
		return links;
	}
	public void setLinks(List<Link> links) {
		this.links = links;
	}
	public void setImage(ImageDTO image) {
		this.image = image;
	}
	
	
}
