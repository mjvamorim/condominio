package com.pegasus.condominio.controller;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pegasus.condominio.model.Proprietario;
import com.pegasus.condominio.repository.Proprietarios;
import com.pegasus.condominio.repository.filter.ProprietarioFilter;

@Controller
@RequestMapping("/proprietarios")
public class ProprietarioController {
	private static final String LISPROPRIETARIOS = "LisProprietarios";
	private static final String FRMPROPRIETARIOS = "FrmProprietarios";
	
	@Autowired
	private Proprietarios proprietarios;
	
	
	//Listar
	@RequestMapping()
	public ModelAndView listar(@ModelAttribute("filtro") ProprietarioFilter filtro) {
		ModelAndView mv = new ModelAndView(LISPROPRIETARIOS);
		
		if (filtro.getNome() == null)  filtro.setNome(""); 
		if (filtro.getCpf() == null)  filtro.setCpf("");
		mv.addObject("proprietarios", 
						proprietarios.findByNomeContainingAndCpfContainingOrderByNome(
								filtro.getNome(),filtro.getCpf()));
		return mv;
	}
	
	//Novo
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(FRMPROPRIETARIOS);
		mv.addObject(new Proprietario());
		return mv;
	}
	
	//Salvar
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Proprietario proprietario, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return FRMPROPRIETARIOS;
		}
		
		try {
			this.proprietarios.save(proprietario);
			attributes.addFlashAttribute("mensagem", "Título salvo com sucesso!");
			return "redirect:/proprietarios/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return FRMPROPRIETARIOS;
		}
	}
	
	//Editar
	@RequestMapping("{idProprietario}")
	public ModelAndView edicao(@PathVariable("idProprietario") Proprietario proprietario) {
		ModelAndView mv = new ModelAndView(FRMPROPRIETARIOS); 
		mv.addObject(proprietario);
		return mv;
	}
	
	//Excluir
	@RequestMapping(value="{idProprietario}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long idProprietario, RedirectAttributes attributes) {
		proprietarios.delete(idProprietario);
		attributes.addFlashAttribute("mensagem", "Proprietario excluído com sucesso!");
		return "redirect:/proprietarios";
	}

	

}
