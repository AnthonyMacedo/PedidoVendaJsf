package com.sathsoft.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.sathsoft.util.NegocioException;
import com.sathsoft.validation.SKU;

@Entity
@Table(schema = "sch_sistema")
public class Produto extends AbstractEntityBase{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false, length = 120)
	private String nome;
	
	@NotBlank @SKU
	@Column(nullable = false, length = 20, unique = true)
	private String sku;
	
	@NotNull
	@Column(name = "valor_unitario", precision = 10, scale = 2)
	private BigDecimal valorUnitario;
	
	@NotNull(message = "é obrigatório")
	@Min(0) @Max(value = 999999, message = "tem o valor muito alto")
	@Column(name = "quantidade_estoque")
	private Integer quantidadeEstoque;
	
	@ManyToOne
	@JoinColumn(name = "categoria_id", nullable = false, foreignKey = @ForeignKey(name = "fk_produto_categoria_id_pk_categoria_id"))
	private Categoria categoria;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}

	public BigDecimal getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(BigDecimal valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Integer getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(Integer quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}
	
	public void baixarEstoque(Integer quantidade) throws NegocioException {
		int novaQuantidade = this.quantidadeEstoque - quantidade;
		
		if (novaQuantidade < 0) {
			throw new NegocioException("Não há disponibilidade no estoque de " + quantidade + " unidades do produto " + this.getSku() + ". " );
		}
		this.setQuantidadeEstoque(novaQuantidade);
	}
	

	public void adicionarEstoque(Integer quantidade) {
		this.setQuantidadeEstoque(quantidadeEstoque + quantidade);
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
		Produto other = (Produto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
