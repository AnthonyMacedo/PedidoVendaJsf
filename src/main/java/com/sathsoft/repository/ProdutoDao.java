package com.sathsoft.repository;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sathsoft.model.Produto;
import com.sathsoft.repository.dto.PesquisaProdutoDto;

public class ProdutoDao extends DaoGeneric<Produto, Long> {
	
	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	
	public Produto buscaPorSku(String sku) {
		try {
			return (Produto) manager.createQuery(" From Produto p where Upper(p.sku) = Upper(:sku)").setParameter("sku", sku).getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public List<Produto> buscarProduto(PesquisaProdutoDto pesquisaProdutoDto, int maxResult) {
		
		String addFiltros = "";
		
		if (pesquisaProdutoDto != null) {
			
			if (pesquisaProdutoDto.getSku() != null && isNotEmpty(pesquisaProdutoDto.getSku())) {
				addFiltros += "and Upper(p.sku) = Upper(:sku) ";
			}
			
			if (pesquisaProdutoDto.getNome() != null && isNotEmpty(pesquisaProdutoDto.getNome())) {
				addFiltros += "and Upper(p.nome) like Upper(:nome) ";
			}
		}
		
		TypedQuery<Produto>	query = manager.createQuery("Select p From Produto p "
			  + "Where p.id is not null "
			  + addFiltros
			  + "Order By p.nome Asc", Produto.class);
		
		if (addFiltros.contains(":sku")) {
			query.setParameter("sku", pesquisaProdutoDto.getSku() + "%");
		}
		
		if (addFiltros.contains(":nome")) {
			query.setParameter("nome", pesquisaProdutoDto.getNome() + "%");
		}
		
		query.setMaxResults(maxResult);
		
		return query.getResultList();
	}


	public List<Produto> buscarPorNome(String nome, int maxResult) {
		
		String addFiltro = "";
		
		if (nome != null && isNotEmpty(nome)) {
			addFiltro += "and Upper(p.nome) like Upper(:nome) ";
		}
		
		TypedQuery<Produto> query = manager.createQuery("Select p From Produto p "
				+ "where p.id is not null "
				+ addFiltro
				+ "order by p.id desc", Produto.class);
		
		if(addFiltro.contains(":nome")) {
			query.setParameter("nome", "%" + nome + "%");
		}
		
		query.setMaxResults(maxResult);
		
		return query.getResultList();
	}
	
	public Produto buscarPorSku(String sku) {
		try {
			String addFiltro = "";

			if (isNotEmpty(sku)) {
				addFiltro += "and Upper(p.sku) = Upper(:sku) ";
			}

			TypedQuery<Produto> query = manager.createQuery("Select p From Produto p " 
					+ "where p.id is not null " 
					+ addFiltro + 
					"order by p.id desc", Produto.class);

			if (addFiltro.contains(":sku")) {
				query.setParameter("sku", sku);
			}

			return query.getSingleResult();
		} catch (Exception e) {
			logger.error( "Sem Resultado buscarPorSku: " + e.getMessage());
			return null;
		}
	}

}