package com.bid90.app.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;


@Entity
@Table(name = "project")
public class Project {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	String title;
	String description;
	
	@OneToMany(mappedBy = "project", cascade=CascadeType.ALL)
	List<Link> link; 
	
	
	@OneToOne(cascade = CascadeType.ALL,orphanRemoval=true)
	Image image;
	
	public Project() {
		super();
	}

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

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}
	public void removeImage(Image image) {
		this.image = null;
		image.setProject(null);
	}

	public List<Link> getLinks() {
		return link;
	}

	public void setLinks(List<Link> links) {
		this.link = links;
	}
	public void addLinks(Link link) {
		this.link.add(link);
		link.project = this;
	}
	public void removeLinks(Link link) {
		this.link.remove(link);
		link.project = null;
	}

}
