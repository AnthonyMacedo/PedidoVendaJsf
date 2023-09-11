package com.sathsoft.service;

import java.util.List;

import javax.inject.Inject;

import com.sathsoft.model.Produto;
import com.sathsoft.repository.ProdutoDao;
import com.sathsoft.repository.dto.PesquisaProdutoDto;
import com.sathsoft.util.NegocioException;
import com.sathsoft.util.jpa.Transactional;

public class ProdutoService extends BaseService{

	private static final long serialVersionUID = 1L;
	private static final int MAX_RESULT_QUERY = 15;

	@Inject
	private ProdutoDao produtoDao;
	
	@Transactional
	public void salvar(Produto produto) throws NegocioException {
		
		if(produtoExistente(produto) != null && !produtoExistente(produto).equals(produto) ) {
			throw new NegocioException("Já existe um produto com SKU informado.");
		}
		
		produtoDao.salvar(produto);
	}
	
	@Transactional
	public void remover(Produto produto) throws NegocioException {
		if (produto != null) {
			produto = produtoDao.buscarPorId(Produto.class, produto.getId());
			produtoDao.remover(produto);
		} else {
			throw new NegocioException("Não foi possível remover o produto");
		}
	}
	
	public List<Produto> buscarTodos() {
		return produtoDao.buscarTodos(Produto.class);
	}
	
	public List<Produto> buscarProduto(PesquisaProdutoDto pesquisaProdutoDto) {
		return produtoDao.buscarProduto(pesquisaProdutoDto, MAX_RESULT_QUERY);
	}

	public Produto buscarPorId(Long id) {
		return produtoDao.buscarPorId(Produto.class, id);
	}
	
	public Produto produtoExistente(Produto produto) {
		return produtoDao.buscaPorSku(produto.getSku()); 
	}

	public List<Produto> buscarPorNome(String nome) {
		return produtoDao.buscarPorNome(nome, MAX_RESULT_QUERY);
	}
	
	public Produto buscarPorSku(String sku) {
		return produtoDao.buscarPorSku(sku);
	}
}
