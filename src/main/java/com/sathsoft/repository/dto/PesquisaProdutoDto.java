package com.sathsoft.repository.dto;

import java.io.Serializable;

import com.sathsoft.validation.SKU;

public class PesquisaProdutoDto implements Serializable {

	private static final long serialVersionUID = 1L;
	 
	@SKU
	private String sku;
	private String nome;

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
