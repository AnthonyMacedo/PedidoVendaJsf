package com.sathsoft.model;

public enum TipoStatusPedido {

	Orcamento("Orçamento"),
	Emitido("Emitido"),
	Cancelado("Cancelado");
	
	private String descricao;
	
	TipoStatusPedido(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
