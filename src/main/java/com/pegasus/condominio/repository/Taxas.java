package com.pegasus.condominio.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pegasus.condominio.model.Taxa;


public interface Taxas extends JpaRepository<Taxa, Long> {

	List<Taxa> findByAnomesContaining(String anomes);
	

}
