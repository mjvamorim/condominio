package com.pegasus.condominio.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;



@Entity
public class Unidade {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idUnidade;
	
	@ManyToOne
    @JoinColumn(name = "id_proprietario")
	private Proprietario proprietario;
	
	@NotEmpty(message="Identificação é obrigatória")
	@Size(max=10, message="Identificação não pode conter mais de 10 caracteres")
	private String identificacao;
	
	private Double fator;
	
	@Size(max=200, message="Observação não pode conter mais de 200 caracteres")
	private String obs;

	public Long getIdUnidade() {
		return idUnidade;
	}

	public void setIdUnidade(Long idUnidade) {
		this.idUnidade = idUnidade;
	}


	public String getIdentificacao() {
		return identificacao;
	}

	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}


	public Double getFator() {
		return fator;
	}

	public void setFator(Double fator) {
		this.fator = fator;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Proprietario getProprietario() {
		return proprietario;
	}

	public void setProprietario(Proprietario proprietario) {
		this.proprietario = proprietario;
	}
}
