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

import com.pegasus.condominio.model.Cobranca;
import com.pegasus.condominio.repository.Unidades;
import com.pegasus.condominio.repository.Cobrancas;
import com.pegasus.condominio.repository.filter.CobrancaFilter;

@Controller
@RequestMapping("/cobrancas")
public class CobrancaController {
	private static final String LIS = "LisCobrancas";
	private static final String FRM = "FrmCobrancas";
	
	@Autowired
	private Cobrancas cobrancas;
	
	@Autowired
	private Unidades unidades;
	
	//Listar
	@RequestMapping()
	public ModelAndView listar(@ModelAttribute("filtro") CobrancaFilter filtro) {
		ModelAndView mv = new ModelAndView(LIS);
		mv.addObject("unidades",unidades.findAll());
		if (filtro.getUnidade() == null)  filtro.setUnidade(""); 
		
		mv.addObject("cobrancas", cobrancas.findAll());
		return mv;
	}
	
	//Novo
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(FRM);
		mv.addObject(new Cobranca());
		mv.addObject("unidades",unidades.findAll());
		return mv;
	}
	
	//Salvar
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Cobranca cobranca, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return FRM;
		}
		
		try {
			this.cobrancas.save(cobranca);
			attributes.addFlashAttribute("mensagem", "Cobranca salva com sucesso!");
			return "redirect:/cobrancas/novo";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return FRM;
		}
	}
	
	//Editar
	@RequestMapping("{idCobranca}")
	public ModelAndView edicao(@PathVariable("idCobranca") Cobranca cobranca) {
		ModelAndView mv = new ModelAndView(FRM); 
		mv.addObject(cobranca);
		mv.addObject("unidades",unidades.findAll());
		return mv;
	}
	
	//Excluir
	@RequestMapping(value="{idCobranca}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long idCobranca, RedirectAttributes attributes) {
		cobrancas.delete(idCobranca);
		attributes.addFlashAttribute("mensagem", "Cobranca excluída com sucesso!");
		return "redirect:/cobrancas";
	}

	

}
