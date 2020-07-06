package com.bid90.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid90.app.model.Project;
import com.bid90.app.repository.ProjectRepository;


@Service
public class ProjectService implements CRUDService<Project>{

	@Autowired
	ProjectRepository projectRepository;
	
	@Override
	public Project create(Project t) {
		
		return projectRepository.save(t);
	}

	@Override
	public Project reade(Long i) {
		return projectRepository.findProjectById(i);
	}

	@Override
	public Project update(Project t) {
		Project updatedProject = projectRepository.findProjectById(t.getId());
		if(updatedProject == null) {
			return null;
		}
		updatedProject.setTitle(t.getTitle());
		updatedProject.setDescription(t.getDescription());
		updatedProject.setImage(t.getImage());
		updatedProject.setLinks(t.getLinks());
		return projectRepository.save(updatedProject);
	}

	public Project updateImage(Project t) {
		Project updatedProject = projectRepository.findProjectById(t.getId());
		if(updatedProject == null) {
			return null;
		}
		updatedProject.setImage(t.getImage());
		
		return projectRepository.save(updatedProject);
	}
	
	@Override
	public void delete(Project t) {
		projectRepository.delete(t);
	}

	public List<Project> findAll() {
		return projectRepository.findAll();
	}

	
	
	

}
