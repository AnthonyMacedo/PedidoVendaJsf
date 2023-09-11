package com.sathsoft.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
public class EnderecoEntrega implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotBlank @Size(max = 150)
	@Column(name = "entrega_logradouro", nullable = false, length = 150)
	private String logradouro;
	
	@NotBlank @Size(max = 20)
	@Column(name = "entrega_numero", nullable = false, length = 20)
	private String numero;
	
	@Column(name = "entrega_complemento", length = 150)
	private String complemento;
	
	@NotBlank @Size(max = 60)
	@Column(name = "entrega_cidade", nullable = false, length = 60)
	private String cidade;
	
	
	@NotBlank @Size(max = 60)
	@Column(name = "entrega_estado", nullable = false, length = 60)
	private String estado;
	
	@NotBlank @Size(max = 2)
	@Column(name = "entrega_uf", nullable = false, length = 2)
	private String uf;
	
	@NotBlank @Size(max = 10)
	@Column(name = "entrega_cep", nullable = false, length = 10)
	private String cep;

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

}
