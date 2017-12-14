package com.pegasus.condominio.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.pegasus.condominio.model.TipoBaixa;
import com.pegasus.condominio.model.Titulo;
import com.pegasus.condominio.repository.Titulos;
import com.pegasus.condominio.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;
	
	public void salvar(Titulo titulo) {
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data inválido");
		}
	}

	public void excluir(Long codigo) {
		titulos.delete(codigo);
	}

	public String receber(Long codigo) {
		Titulo titulo = titulos.findOne(codigo);
		titulo.setBaixa(TipoBaixa.BANCO);
		titulos.save(titulo);
		
		return TipoBaixa.BANCO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro) {
		String descricao = (filtro.getDescricao() == null) ? "%" : filtro.getDescricao();
		return titulos.findByDescricaoContaining(descricao);
	}
	
}
