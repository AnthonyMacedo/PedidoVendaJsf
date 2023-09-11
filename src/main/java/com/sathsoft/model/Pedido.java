package com.sathsoft.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "pedido", schema = "sch_sistema")
public class Pedido extends AbstractEntityBase {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_criacao" , nullable = false)
	private Date dataCriacao;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "data_entrega", nullable = false)
	private Date dataEntrega;
	
	@Column(columnDefinition = "text")
	private String observacao;
	
	@NotNull
	@Column(name = "valor_frete", precision = 10, scale = 2)
	private BigDecimal valorFrete;
	
	@NotNull
	@Column(name = "valor_desconto", precision = 10, scale = 2)
	private BigDecimal valorDesconto;
	
	@Column(name = "valor_total", precision = 10, scale = 2)
	private BigDecimal valorTotal;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private TipoStatusPedido status;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "forma_pagamento", nullable = false, length = 40)
	private TipoFormaPagamento formaPagamento;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "vendedor_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_vendedor_id_pk_usuario_id"))
	private Usuario vendedor;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "cliente_id", nullable = false, foreignKey = @ForeignKey(name = "fk_pedido_cliente_id_pk_cliente_id"))
	private Cliente cliente;
	
	@Embedded
	private EnderecoEntrega enderecoEntrega;
	
	@OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<PedidoItem> itens;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public BigDecimal getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(BigDecimal valorFrete) {
		this.valorFrete = valorFrete;
	}

	public BigDecimal getValorDesconto() {
		return valorDesconto;
	}

	public void setValorDesconto(BigDecimal valorDesconto) {
		this.valorDesconto = valorDesconto;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}

	public TipoStatusPedido getStatus() {
		return status;
	}

	public void setStatus(TipoStatusPedido status) {
		this.status = status;
	}

	public TipoFormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(TipoFormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public EnderecoEntrega getEnderecoEntrega() {
		return enderecoEntrega;
	}

	public void setEnderecoEntrega(EnderecoEntrega enderecoEntrega) {
		this.enderecoEntrega = enderecoEntrega;
	}

	public List<PedidoItem> getItens() {
		return itens;
	}

	public void setItens(List<PedidoItem> itens) {
		this.itens = itens;
	}
	
	public boolean isNovo() {
		return getId() == null;
	}
	
	public boolean isExistente() {
		return !isNovo();
	}

	public void recalcularValorTotal() {
		BigDecimal total = BigDecimal.ZERO;
		
		total = total.add(this.valorFrete).subtract(this.valorDesconto);
		
		for (PedidoItem item : this.itens) {
			if (item.getProduto() != null && item.getProduto().getId() != null) {
				total = total.add(item.getValorTotal());
			}
		}
		this.setValorTotal(total);
	}
	
	public BigDecimal getValorSubtotal() {
		return this.valorTotal.subtract(this.valorFrete).add(this.valorDesconto);
	}
	
	public void addicionarItemVazio() {
		if(this.isOrcamento()) {
			Produto produto = new Produto();
			
			PedidoItem item = new PedidoItem();
			item.setProduto(produto);
			item.setValorUnitario(new BigDecimal(0));
			item.setQuantidade(1);
			item.setPedido(this);
			
			this.getItens().add(0, item);
		}
	}

	public boolean isOrcamento() {
		return TipoStatusPedido.Orcamento.equals(this.status);
	}
	
	public void iniciar() {
		this.dataCriacao = new Date();
		this.enderecoEntrega = new EnderecoEntrega();
		this.status = TipoStatusPedido.Orcamento;
		this.valorDesconto = BigDecimal.ZERO;
		this.valorFrete = BigDecimal.ZERO;
		this.valorTotal = BigDecimal.ZERO;
		this.itens = new ArrayList<PedidoItem>();
	}
	
	public void removerItemVazio() {
		PedidoItem primeiroItem = this.getItens().get(0);
		
		if (primeiroItem != null && primeiroItem.getProduto().getId() == null) {
			this.getItens().remove(0);
		}
	}
	
	public boolean isValorTotalNegativo() {
		return this.getValorTotal().compareTo(BigDecimal.ZERO) < 0;
	}
	
	public boolean isEmitido() {
		return TipoStatusPedido.Emitido.equals(this.status);
	}
	
	public boolean isNaoEmissivel() {
		return !isEmissivel();
	}

	public boolean isEmissivel() {
		return isExistente() && this.isOrcamento();
	}
	
	public boolean isNaoCancelavel() {
		return !isCancelavel();
	}

	public boolean isCancelavel() {
		return this.isExistente() && !this.isCancelado();
	}

	public boolean isCancelado() {
		return this.status.equals(TipoStatusPedido.Cancelado);
	}
	
	public boolean isAlteravel() {
		return this.isOrcamento();
	}
	
	public boolean isNaoAlteravel() {
		return !this.isAlteravel();
	}
	
	public boolean isNaoEnviavelPorEmail() {
		return this.isNovo() || this.isCancelado();
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
