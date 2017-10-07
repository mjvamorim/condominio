package com.pegasus.condominio.security.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.pegasus.condominio.security.model.User;
import com.pegasus.condominio.security.service.UserService;

@Component
public class UserValidator implements Validator {
	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> aClass) {
		return User.class.equals(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		User user = (User) o;


		//Testar apensas em usuarios novos
		if (user.getId()  == null) {

			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "user.username.notempty");
			if (user.getUsername().length() < 6 || user.getUsername().length() > 32) {
				errors.rejectValue("username", "user.username.size");
			}
			if (userService.findByusername(user.getUsername()) != null) {
				errors.rejectValue("username", "user.username.duplicated");
			}
			if (!user.getUsernameConfirm().equals(user.getUsername())) {
				errors.rejectValue("usernameConfirm", "user.usernameConfirm.diff");
			}
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "user.password.notempty");
			if (user.getPassword().length() < 8 || user.getPassword().length() > 32) {
				errors.rejectValue("password", "user.password.size");
			}

			if (!user.getPasswordConfirm().equals(user.getPassword())) {
				errors.rejectValue("passwordConfirm", "user.passwordConfirm.diff");
			}
		}
	}
}
