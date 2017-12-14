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

import com.pegasus.condominio.model.Taxa;
import com.pegasus.condominio.repository.Taxas;
import com.pegasus.condominio.repository.filter.TaxaFilter;

@Controller
@RequestMapping("/taxas")
public class TaxaController {
	private static final String LIS = "LisTaxas";
	private static final String FRM = "FrmTaxas";
	
	@Autowired
	private Taxas taxas;
	
	
	//Listar
	@RequestMapping()
	public ModelAndView listar(@ModelAttribute("filtro") TaxaFilter filtro) {
		ModelAndView mv = new ModelAndView(LIS);
		
		if (filtro.getAnomes() == null)  filtro.setAnomes(""); 
		mv.addObject("taxas", 
						taxas.findByAnomesContaining(filtro.getAnomes()));
		return mv;
	}
	
	//Novo
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(FRM);
		mv.addObject(new Taxa());
		return mv;
	}
	
	//Salvar
	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Taxa taxa, Errors errors, RedirectAttributes attributes) {
		if (errors.hasErrors()) {
			return FRM;
		}
		
		try {
			this.taxas.save(taxa);
			attributes.addFlashAttribute("mensagem", "Taxa salva com sucesso!");
			return "redirect:/taxas";
		} catch (IllegalArgumentException e) {
			errors.rejectValue("dataVencimento", null, e.getMessage());
			return FRM;
		}
	}
	
	//Editar
	@RequestMapping("{idTaxa}")
	public ModelAndView edicao(@PathVariable("idTaxa") Taxa taxa) {
		ModelAndView mv = new ModelAndView(FRM); 
		mv.addObject(taxa);
		return mv;
	}
	
	//Excluir
	@RequestMapping(value="{idTaxa}", method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long idTaxa, RedirectAttributes attributes) {
		taxas.delete(idTaxa);
		attributes.addFlashAttribute("mensagem", "Taxa excluída com sucesso!");
		return "redirect:/taxas";
	}

	

}
