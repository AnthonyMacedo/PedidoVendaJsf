package com.sathsoft.repository.dto;

import java.io.Serializable;
import java.util.Date;

import com.sathsoft.model.TipoStatusPedido;

public class PesquisaPedidoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long pedidoIdInicial;
	private Long pedidoIdFinal;
	private Date dataInicial;
	private Date dataFinal;
	private String nomeVendedor;
	private String nomeCliente;
	private TipoStatusPedido[] statusPedido;

	public Long getPedidoIdInicial() {
		return pedidoIdInicial;
	}

	public void setPedidoIdInicial(Long pedidoIdInicial) {
		this.pedidoIdInicial = pedidoIdInicial;
	}

	public Long getPedidoIdFinal() {
		return pedidoIdFinal;
	}

	public void setPedidoIdFinal(Long pedidoIdFinal) {
		this.pedidoIdFinal = pedidoIdFinal;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getNomeVendedor() {
		return nomeVendedor;
	}

	public void setNomeVendedor(String nomeVendedor) {
		this.nomeVendedor = nomeVendedor;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public TipoStatusPedido[] getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(TipoStatusPedido[] statusPedido) {
		this.statusPedido = statusPedido;
	}
	
}
