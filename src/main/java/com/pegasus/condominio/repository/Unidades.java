package com.pegasus.condominio.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pegasus.condominio.model.Unidade;


public interface Unidades extends JpaRepository<Unidade, Long> {

	List<Unidade> findByIdentificacaoContaining(String identificacao);
	

}
