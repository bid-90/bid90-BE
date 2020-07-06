package com.bid90.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bid90.app.model.Skill;

public interface SkillRepository extends JpaRepository<Skill, Long> {

	Skill findSkillById(Long i);

	Skill findSkillByName(String name);

}
