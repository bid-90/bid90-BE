package com.bid90.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bid90.app.model.Link;

public interface LinkRepository extends JpaRepository<Link,Long>{

	Link findLinkById(Long i);

}
