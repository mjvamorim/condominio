package com.pegasus.condominio.security.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;
import com.pegasus.condominio.security.filter.UserFilter;
import com.pegasus.condominio.security.model.Role;
import com.pegasus.condominio.security.model.User;
import com.pegasus.condominio.security.repository.RoleRepository;
import com.pegasus.condominio.security.repository.UserRepository;
import com.pegasus.condominio.security.service.SecurityService;
import com.pegasus.condominio.security.service.UserService;
import com.pegasus.condominio.security.validator.UserValidator;


@Controller
@RequestMapping("/security")
public class SecurityController {
	private static final String LOGIN = "security/Login";
	private static final String REGISTRATION = "security/Registration";
	private static final String PASSWORD = "security/ChangePassword";
	private static final String FORGOTPASS = "security/ForgotPassword";
	private static final String LIS = "security/UsersLis";
	private static final String FRM = "security/UsersFrm";

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private SecurityService securityService;
	@Autowired
	private UserValidator userValidator;

	@SuppressWarnings("unused")
	private class RoleJs {
		String id;
		String name;
		public RoleJs(String id, String name) {
			this.id = id;
			this.name = name;
		}
	}

	// UsersLis
	@RequestMapping("/users")
	public ModelAndView list(@ModelAttribute("filtro") UserFilter filtro) {
		ModelAndView mv = new ModelAndView(LIS);
		mv.addObject("users", userRepository.findAll());
		ArrayList<RoleJs> allRoles =  new ArrayList <RoleJs>();
		Gson gson = new Gson();
		List <Role> allRolesDb = roleRepository.findAll();
		for(Role r : allRolesDb) {
			allRoles.add(new RoleJs(r.getId().toString(),r.getName()));
		}
		mv.addObject("allRolesJs",gson.toJson(allRoles));
		mv.addObject("allRolesDb",allRolesDb);
		return mv;
	}


	@RequestMapping(value = "/users/permissoes/{id}", method = RequestMethod.GET)
	public @ResponseBody String userRoles(@PathVariable("id") User user, @RequestParam("ck") List <String> permissoes) {
		// Gerar Set <Role> e ArrayJs com as "permissoes" passadas
		Set<Role> roles = new HashSet<Role>();
		ArrayList<RoleJs> userRoles =  new ArrayList <RoleJs>();
		Gson gson = new Gson();
		for (String p : permissoes) {
			long l = Long.parseLong(p);
			Role r = roleRepository.findOne(l);
			roles.add(r);
			userRoles.add(new RoleJs(r.getId().toString(),r.getName()));
			System.out.println("Usuario:"+user.getId()+" Permissão: "+p);
		}
		//Gravar permissoes passadas
		userService.changeRoles(user,roles);

		return gson.toJson(userRoles);
	}

	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public String registration(Model model) {
		model.addAttribute("user", new User());
		return REGISTRATION;
	}

	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public String registration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return REGISTRATION;
		}
		userService.create(user);
		securityService.autologin(user.getUsername(), user.getPasswordConfirm());
		return "redirect:/";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");
		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");
		return LOGIN;
	}

	@RequestMapping(value = "/changepassword/{id}", method = RequestMethod.GET)
	public String changePassword(@PathVariable("id") User user,  RedirectAttributes attributes) {
		attributes.addFlashAttribute("mensagem", "Email troca senha enviado com sucesso!");
		userService.clearPassword(user);
		return "redirect:/";
	}

	@RequestMapping(value = "/changepassword", method = RequestMethod.GET)
	public ModelAndView changePassword(Authentication authentication) {
		User user = userService.findByusername(authentication.getName());
		ModelAndView model = new ModelAndView(PASSWORD);
		model.addObject("user", user);
		return model;
	}


	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	public String changepassword(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		userValidator.validate(user, bindingResult);
		if (bindingResult.hasErrors()) {
			return PASSWORD;
		}
		userService.changePassword(user);
		securityService.autologin(user.getUsername(), user.getPasswordConfirm());
		return "redirect:/";
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public String forgotpassword(Model model) {
		model.addAttribute("user", new User());
		return FORGOTPASS;
	}

	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST)
	public String forgotpassword(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
		if (user.getUsername().length() < 6) {
			model.addAttribute("error", "Email inválido.");
			return FORGOTPASS;
		}
		userService.clearPassword(user);
		return "redirect:/";
	}



	// Setting
	@RequestMapping(value = "/users/settings", method = RequestMethod.GET)
	public ModelAndView currentUserName(Authentication authentication) {
		User user = userRepository.findByusername(authentication.getName());
		ModelAndView mv = new ModelAndView(FRM);
		mv.addObject(user);
		return mv;
	}



	// Create ->Igual ao Registration Senha Obrigatória
	@RequestMapping("/users/create")
	public ModelAndView create() {
		ModelAndView mv = new ModelAndView(FRM);
		mv.addObject(new User());
		return mv;
	}

	// Save
	@RequestMapping(value = "/users/save", method = RequestMethod.POST)
	public String save(@Valid User user, Errors errors, RedirectAttributes attributes) {
		userValidator.validate(user, errors); 
		if (errors.hasErrors()) {
			return FRM;
		}
		try {
			if(user.getId()!=null) {
				this.userService.update(user);
			} else {
				this.userService.create(user);
			}
			attributes.addFlashAttribute("mensagem", "Usuário salvo com sucesso!");
			return "redirect:/";
		} catch (IllegalArgumentException e) {
			return FRM;
		}
	}

	// Editar
	@RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
	public ModelAndView edit(@PathVariable("id") User user) {
		ModelAndView mv = new ModelAndView(FRM);
		mv.addObject(user);
		return mv;
	}

	// Delete
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		userRepository.delete(id);
		attributes.addFlashAttribute("mensagem", "Usuário excluído com sucesso!");
		return "redirect:/security/users";
	}

	// Login As
	@RequestMapping(value = "/users/login_as/{id}", method = RequestMethod.GET)
	public String loginAs(@PathVariable("id") Long id,  RedirectAttributes attributes) throws DataAccessException, SQLException, IOException {
		User user=userRepository.findOne(id);
		System.out.println(user.getUsername());		
		securityService.autologin(user.getUsername(), user.getPassword());
		return "redirect:/";
	}
}
