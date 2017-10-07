package com.pegasus.condominio.security.service;

import java.util.Set;

import com.pegasus.condominio.security.model.Role;
import com.pegasus.condominio.security.model.User;

public interface UserService {
    User findByusername(String username);
    void create(User user);
    void update(User user);
    void changePassword(User user);
	void clearPassword(User user);
	void changeRoles(User user, Set<Role> roles);
	void useDb(User user) throws Exception;
}
