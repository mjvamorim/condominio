package com.pegasus.condominio.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;



@Entity
public class Proprietario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idProprietario;
	
	@NotEmpty(message = "Nome é obrigatória")
	@Size(max = 60, message = "A nome não pode conter mais de 60 caracteres")
	private String nome;
	
	@NotEmpty(message = "CPF é obrigatória")
	@Size(max = 18, message = "O cpf/cnpj não pode conter mais de 18 caracteres")
	private String cpf;
	
	@NotEmpty(message = "Identidade é obrigatória")
	@Size(max = 20, message = "A Identidade não pode conter mais de 20 caracteres")
	private String ident;
	
	@NotEmpty(message = "Cep é obrigatória")
	@Size(max = 9, message = "O Cep não pode conter mais de 09 caracteres")
	private String cep;
	
	@NotEmpty(message = "Endereço é obrigatória")
	@Size(max = 60, message = "O Endereço não pode conter mais de 60 caracteres")
	private String endereco;
	
	@Size(max = 60, message = "O Complemento não pode conter mais de 60 caracteres")
	private String complemento;
	
	@NotEmpty(message = "Bairro é obrigatória")
	@Size(max = 30, message = "O Bairro não pode conter mais de 30 caracteres")
	private String bairro;
	
	@NotEmpty(message = "Cidade é obrigatória")
	@Size(max = 30, message = "A Cidade não pode conter mais de 30 caracteres")
	private String cidade;
	
	@NotEmpty(message = "UF é obrigatória")
	@Size(max = 2, message = "O UF não pode conter mais de 60 caracteres")
	private String uf;
	
	@Size(max = 20, message = "O Celular não pode conter mais de 20 caracteres")
	private String celular;

	@Size(max = 20, message = "O Telefone não pode conter mais de 20 caracteres")
	private String telefone;
	
	@Size(max = 60, message = "O Email não pode conter mais de 60 caracteres")
	private String email;
	
	@Size(max = 20, message = "O Senha não pode conter mais de 60 caracteres")
	private String senha;
	
	@OneToMany(mappedBy = "proprietario", cascade = CascadeType.ALL)
	private Set<Unidade> unidades;
	
	public Long getIdProprietario() {
		return idProprietario;
	}

	public void setIdProprietario(Long idProprietario) {
		this.idProprietario = idProprietario;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Set<Unidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(Set<Unidade> unidades) {
		this.unidades = unidades;
	}



}
