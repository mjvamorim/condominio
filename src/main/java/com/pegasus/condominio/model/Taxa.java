package com.pegasus.condominio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;




@Entity
public class Taxa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idTaxa;
	
	private String anomes;
	
	private Double valorCondominio;
	
	private Double ValorExtra;

	public Long getIdTaxa() {
		return idTaxa;
	}

	public void setIdTaxa(Long idTaxa) {
		this.idTaxa = idTaxa;
	}
	
	public String getAnomes() {
		return anomes;
	}

	public void setAnomes(String anomes) {
		this.anomes = anomes;
	}

	public Double getValorCondominio() {
		return valorCondominio;
	}

	public void setValorCondominio(Double valorCondominio) {
		this.valorCondominio = valorCondominio;
	}

	public Double getValorExtra() {
		return ValorExtra;
	}

	public void setValorExtra(Double valorExtra) {
		ValorExtra = valorExtra;
	}


}
