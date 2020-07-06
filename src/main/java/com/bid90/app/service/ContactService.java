package com.bid90.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bid90.app.model.Contact;
import com.bid90.app.repository.ContactRepository;

@Service
public class ContactService implements CRUDService<Contact>{

	@Autowired
	ContactRepository contactRepository;
	
	@Override
	public Contact create(Contact t) {
		
		return contactRepository.save(t);
	}

	@Override
	public Contact reade(Long i) {
		
		return contactRepository.findContactById(i);
	}

	@Override
	public Contact update(Contact t) {
		Contact updateContact = contactRepository.findContactById(t.getId());
		if(updateContact == null) {
			return null;
		}
		updateContact.setAbout(t.getAbout());
		updateContact.setType(t.getType());
		updateContact.setValue(t.getValue());
		return contactRepository.save(updateContact);
	}

	@Override
	public void delete(Contact t) {
		contactRepository.delete(t);
		
	}

	public Contact findContactById(Long id) {
		return contactRepository.findContactById(id);
	}

	public List<Contact> findAll() {
		
		return contactRepository.findAll();
	}

	
}
