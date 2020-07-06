package com.bid90.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bid90.app.model.Education;

public interface EducationRepository extends JpaRepository<Education, Long> {

	Education findEducationById(Long id);

}
