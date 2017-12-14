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

import com.pegasus.condominio.model.Unidade;
import com.pegasus.condominio.repository.Proprietarios;
import com.pegasus.condominio.repository.Unidades;
import com.pegasus.condominio.repository.filter.UnidadeFilter;

@Controller
@RequestMapping("/unidades")
public class UnidadeController {
	private static final String LIS = "LisUnidades";
	private static final String FRM = "FrmUnidades";
	
	@Autowired
	private Unidades unidades;
	
	@Autowired
	private Proprietarios proprietarios;
	
	//Listar
	@RequestMapping()
	public ModelAndView listar(@ModelAttribute("filtro") UnidadeFilter filtro) {
		ModelAndView mv = new ModelAndView(LIS);
		
		if (filtro.getIdentificacao() == null)  filtro.setIdentificacao(""); 
		mv.addObject("unidades", 
						unidades.findByIdentificacaoContaining(filtro.getIdentificacao()));
		return mv;
	}
	
	//Novo
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(FRM);
		mv.addObject(new Unidade());
		mv.addObject("proprietarios",proprietarios.findAll());
		return mv;
	}
	
	//Salvar
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Unidade unidade, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return FRM;
		}
		
		try {
			this.unidades.save(unidade);
			attributes.addFlashAttribute("mensagem", "Unidade salva com sucesso!");
			return "redirect:/unidades/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return FRM;
		}
	}
	
	//Editar
	@RequestMapping("{idUnidade}")
	public ModelAndView edicao(@PathVariable("idUnidade") Unidade unidade) {
		ModelAndView mv = new ModelAndView(FRM); 
		mv.addObject(unidade);
		mv.addObject("proprietarios",proprietarios.findAll());
		return mv;
	}
	
	//Excluir
	@RequestMapping(value="{idUnidade}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long idUnidade, RedirectAttributes attributes) {
		unidades.delete(idUnidade);
		attributes.addFlashAttribute("mensagem", "Unidade excluída com sucesso!");
		return "redirect:/unidades";
	}

	

}
