package com.bid90.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid90.app.model.Link;
import com.bid90.app.repository.LinkRepository;

@Service
public class LinkService implements CRUDService<Link>{

	@Autowired
	LinkRepository linkRepository;
	
	@Override
	public Link create(Link t) {
		return linkRepository.save(t);
	}

	@Override
	public Link reade(Long i) {
	
		return linkRepository.findLinkById(i);
	}

	@Override
	public Link update(Link t) {
		Link updateLink = linkRepository.findLinkById(t.getId());
		if(updateLink == null) {
			return null;
		}
		updateLink.setLink(t.getLink());
		updateLink.setType(t.getType());
		return linkRepository.save(updateLink);
	}

	@Override
	public void delete(Link t) {
		linkRepository.delete(t);
		
	}


}
