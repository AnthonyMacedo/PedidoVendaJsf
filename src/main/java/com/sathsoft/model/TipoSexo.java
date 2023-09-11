package com.sathsoft.model;

public enum TipoSexo {
	
	M("Masculino"),
	F("Feminino");
	
	private String descricao;
	
	TipoSexo(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
	
}
