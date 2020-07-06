package com.bid90.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bid90.app.model.Education;
import com.bid90.app.repository.EducationRepository;

@Service
public class EducationService implements CRUDService<Education>{

	@Autowired
	EducationRepository educationRepository;

	public Education findEducationById(Long id) {
		return educationRepository.findEducationById(id);
	}

	@Override
	public Education create(Education t) {
		return educationRepository.save(t);
	}

	@Override
	public Education reade(Long i) {
		return educationRepository.findEducationById(i);
	}

	@Override
	public Education update(Education t) {
		Education updateEducation = educationRepository.findEducationById(t.getId());
		if(updateEducation == null) {
			return null;
		}
		updateEducation.setCity(t.getCity());
		updateEducation.setCountry(t.getCountry());
		updateEducation.setDate(t.getDate());
		updateEducation.setOrganisation(t.getOrganisation());
		updateEducation.setQualification(t.getQualification());
		updateEducation.setAbout(t.getAbout());
		return educationRepository.save(updateEducation);
	}

	@Override
	public void delete(Education t) {
		educationRepository.delete(t);
	}

	public List<Education> findAll(){
		return educationRepository.findAll();
	}
	
}
