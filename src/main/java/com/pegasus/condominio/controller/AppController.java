package com.pegasus.condominio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import com.pegasus.condominio.config.GlobalProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AppController {
	
	@Autowired
	private GlobalProperties global;

	
	 @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
	    public String index(Model model) {

	        System.out.println(global.toString());

	        return "index";
	        
	    }
		@RequestMapping(value = "/403", method = RequestMethod.GET)
		public ModelAndView accesssDenied(Authentication user) {
			ModelAndView model = new ModelAndView("403");
			model.addObject("msg","Você não tem acesso a está página!");
			return model;
		}
}
