package com.sathsoft.model;

public enum TipoPessoa {

	F("Fisica"),
	J("Jurídica");
	
	private String descricao;
	
	TipoPessoa(String descricao){
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
