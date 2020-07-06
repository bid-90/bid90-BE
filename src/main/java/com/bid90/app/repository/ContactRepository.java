package com.bid90.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bid90.app.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {

	Contact findContactById(Long i);

}
