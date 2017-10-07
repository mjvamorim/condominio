package com.pegasus.condominio.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pegasus.condominio.model.Proprietario;


public interface Proprietarios extends JpaRepository<Proprietario, Long> {

	List<Proprietario> findByNomeContaining(String nome);
	List<Proprietario> findByNomeContainingAndCpfContaining(String nome,String cpf);
	List<Proprietario> findByNomeContainingAndCpfContainingOrderByNome(String nome, String cpf);

	 //@Query("select p from Proprietario p where p.nome like %?1% ")
	 //public List<Proprietario> findByNomeContaining(String nome);

	 //@Query("select p from Proprietario p where ?1 ")
	 //public List<Proprietario> where(String condicao);

}
