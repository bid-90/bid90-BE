package com.bid90.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid90.app.model.Skill;
import com.bid90.app.repository.SkillRepository;

@Service
public class SkillService implements CRUDService<Skill>{

	@Autowired
	SkillRepository skillRepository;
	
	@Override
	public Skill create(Skill t) {
		
		return skillRepository.save(t);
	}

	@Override
	public Skill reade(Long i) {
		return skillRepository.findSkillById(i);
	}

	@Override
	public Skill update(Skill t) {
		Skill updateSkill = skillRepository.findSkillById(t.getId());
		if(updateSkill == null) {
			return null;
		}
		updateSkill.setName(t.getName());
		updateSkill.setValue(t.getValue());
		return skillRepository.save(updateSkill);
	}

	@Override
	public void delete(Skill t) {
		skillRepository.delete(t);
	}
	
	public List<Skill> findAll(){
		return skillRepository.findAll();
	}

	public Skill findSkillByName(String name) {
		return skillRepository.findSkillByName(name);
	
	}

	public Skill findSkillById(Long id) {
		return skillRepository.findSkillById(id);
	}
	

}
