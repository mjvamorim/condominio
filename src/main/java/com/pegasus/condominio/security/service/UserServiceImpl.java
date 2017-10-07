package com.pegasus.condominio.security.service;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pegasus.condominio.config.MyUtils;
import com.pegasus.condominio.config.SenderMailService;
import com.pegasus.condominio.security.model.Role;
import com.pegasus.condominio.security.model.User;
import com.pegasus.condominio.security.repository.RoleRepository;
import com.pegasus.condominio.security.repository.UserRepository;
import com.pegasus.condominio.tenant.TenantContext;


@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	SenderMailService senderMailService;

	@Override
	public User findByusername(String username) {
		return userRepository.findByusername(username);
	}

	@Override
	public void create(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepository.findByNameContaining("USER"));
		userRepository.save(user);
	}

	@Override
	public void update(User user) {
		User existing = userRepository.findOne(user.getId());
		user.setPassword(existing.getPassword());
		MyUtils.copyNonNullProperties(user, existing);
		user.setRoles(existing.getRoles());
		userRepository.save(user);
	}
	@Override
	public void changePassword(User user){
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		User existing = userRepository.findOne(user.getId());
		MyUtils.copyNonNullProperties(user, existing);
		user.setRoles(existing.getRoles());
		userRepository.save(user);
	}

	public void clearPassword(User user){
		senderMailService.sendMail(user.getUsername(), "Titulo", "Mensagem");
	}

	@Override
	public void changeRoles(User user, Set<Role> roles){
		User existing = userRepository.findOne(user.getId());
		user.setRoles(roles);
		MyUtils.copyNonNullProperties(user, existing);
		userRepository.save(user);
	}
	@Override
	public void useDb(User user) {
		String tenant = "db"+user.getId();
		TenantContext.setCurrentTenant(tenant);
		System.out.println(tenant);		
		MyUtils.exportDDL();
		MyUtils.createDB(tenant);;

	}
}
