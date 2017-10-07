package com.pegasus.condominio.security.repository;

import java.util.ArrayList;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pegasus.condominio.security.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long>{
	 
	Set <Role> findByNameContaining(String name);
	
}
