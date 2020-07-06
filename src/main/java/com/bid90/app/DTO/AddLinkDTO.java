package com.bid90.app.DTO;

import java.util.List;

public class AddLinkDTO {
	Long projectId;
	List<LinksDTO> links;
	
	public AddLinkDTO() {
		super();
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public List<LinksDTO> getLinks() {
		return links;
	}
	public void setLinks(List<LinksDTO> links) {
		this.links = links;
	}
}
