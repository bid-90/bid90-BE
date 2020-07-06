package com.bid90.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bid90.app.model.Project;


public interface ProjectRepository extends JpaRepository<Project, Long>{

	Project findPostById(Long i);

	Project findProjectById(Long id);

}
