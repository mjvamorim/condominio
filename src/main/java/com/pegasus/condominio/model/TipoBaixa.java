package com.pegasus.condominio.model;

public enum TipoBaixa{

	CAIXA("Caixa"),
	BANCO("Banco");
	
	private String descricao;
	
	TipoBaixa(String descricao) {
		this.descricao = descricao;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
}
