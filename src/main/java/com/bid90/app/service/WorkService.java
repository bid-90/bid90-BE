package com.bid90.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid90.app.model.Work;
import com.bid90.app.repository.WorkRepository;

@Service
public class WorkService implements CRUDService<Work> {

	@Autowired
	WorkRepository workRepository;
	
	@Override
	public Work create(Work t) {
		
		return workRepository.save(t);
	}

	@Override
	public Work reade(Long i) {
		return workRepository.findWorkById(i);
	}

	@Override
	public Work update(Work t) {
		Work updatedWork = workRepository.findWorkById(t.getId());
		if(updatedWork == null) {
			return null;
		}
		updatedWork.setCity(t.getCity());
		updatedWork.setCountry(t.getCountry());
		updatedWork.setDate(t.getDate());
		updatedWork.setEmployer(t.getEmployer());
		updatedWork.setAbout(t.getAbout());
		updatedWork.setQualification(t.getQualification());
		return workRepository.save(updatedWork);
	}

	@Override
	public void delete(Work t) {
		workRepository.delete(t);
	}

	public Work findWorkById(Long id) {
		return workRepository.findWorkById(id);
	}

	public List<Work> findAll() {
		
		return workRepository.findAll();
	}

}
