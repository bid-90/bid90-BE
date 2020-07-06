package com.bid90.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid90.app.model.About;
import com.bid90.app.model.Image;
import com.bid90.app.repository.AboutReopsitory;


@Service
public class AboutService implements CRUDService<About> {

	@Autowired
	AboutReopsitory aboutReopsitory;
	
	@Override
	public About create(About t) {
		
		return aboutReopsitory.save(t);
	}

	@Override
	public About reade(Long i) {
		
		return aboutReopsitory.findAboutById(i);
	}

	@Override
	public About update(About t) {
		About about = aboutReopsitory.findAboutById(t.getId());
		if(about == null) {
			return null;
		}
		if(about.getImage() == null) {
			about.setImage(new Image());
		}else {
			about.setImage(t.getImage());
		}
		about.setDateOfBirth(t.getDateOfBirth());
		about.setName(t.getName());
		about.setOcupation(t.getOcupation());
		return aboutReopsitory.save(about);
	}


	@Override
	public void delete(About t) {
		aboutReopsitory.delete(t);
		
	}

}
