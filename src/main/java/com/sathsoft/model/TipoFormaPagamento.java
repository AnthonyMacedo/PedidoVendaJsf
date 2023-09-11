package com.sathsoft.model;

public enum TipoFormaPagamento {
	
	Dinheiro("Dinheiro"),
	CartaoCredito("Cartão Crédito"),
	CartaoDebito("Cartão Debito"),
	Cheque("Cheque"),
	Boleto("Boleto"),
	Deposito("Deposito Bancario");
	
	private String descricao;
	
	TipoFormaPagamento(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
