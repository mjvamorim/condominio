package com.pegasus.condominio.model;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

@Entity
public class Taxa {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idTaxa;
	
	private String anomes;
	
	@NotNull(message = "Valor do Condominio é obrigatório")
	@DecimalMin(value = "0.01", message = "Valor não pode ser menor que 0,01")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal valorCondominio;
	
	
	@NotNull(message = "Valor Extra é obrigatório")
	@DecimalMin(value = "0.00", message = "Valor não pode ser menor que 0,00")
	@DecimalMax(value = "9999999.99", message = "Valor não pode ser maior que 9.999.999,99")
	@NumberFormat(pattern = "#,##0.00")
	private BigDecimal ValorExtra;

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

	public BigDecimal getValorCondominio() {
		return valorCondominio;
	}

	public void setValorCondominio(BigDecimal valorCondominio) {
		this.valorCondominio = valorCondominio;
	}

	public BigDecimal getValorExtra() {
		return ValorExtra;
	}

	public void setValorExtra(BigDecimal valorExtra) {
		ValorExtra = valorExtra;
	}


}
