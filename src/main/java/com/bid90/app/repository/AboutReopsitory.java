package com.bid90.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.bid90.app.model.About;


public interface AboutReopsitory extends JpaRepository<About, Long>{
	About findAboutById(Long i);

}
