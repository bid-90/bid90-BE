package com.bid90.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bid90.app.model.Work;

public interface WorkRepository extends JpaRepository<Work, Long>{

	Work findWorkById(Long i);

}
