package com.sathsoft.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.sathsoft.model.Categoria;
import com.sathsoft.model.Produto;
import com.sathsoft.repository.dto.PesquisaProdutoDto;
import com.sathsoft.service.CategoriaService;
import com.sathsoft.service.ProdutoService;
import com.sathsoft.util.jsf.FacesUtil;

@Named
@ViewScoped
public class ProdutoBean extends BaseManagedBean {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoService produtoService;
	@Inject
	private CategoriaService categoriaService;

	private Produto produto;
	private Categoria categoriaPai;

	private PesquisaProdutoDto pesquisaProdutoDto;

	private Produto produtoSelecionado;
	
	private List<Produto> listaProdutos;
	private List<Categoria> categoriasRaizes;
	private List<Categoria> subCategorias;

	public void inicializar() {

		if (this.produto == null) {
			limpar();
		}

		if (FacesUtil.isNotPostback()) {
			this.categoriasRaizes = this.categoriaService.categoriasRaizes();
		}

		if (this.categoriaPai != null) {
			this.carregarSubCategorias();
		}
	}

	public void carregarTodos() {
		pesquisaProdutoDto = new PesquisaProdutoDto();

		if (listaProdutos == null) {
			listaProdutos = produtoService.buscarTodos();
		}
	}

	private void limpar() {
		produto = new Produto();
		categoriaPai = null;
		subCategorias = new ArrayList<>();
	}

	public void salvar() {

		try {
			this.produtoService.salvar(produto);
			this.limpar();
			super.addInfoMessage("Salvo com sucesso.");

		} catch (Exception e) {
			super.addErrorMessage(e.getMessage());
		}
	}

	public void remover() {
		try {
			produtoService.remover(this.produto);
		} catch (Exception e) {
			super.addErrorMessage(e.getMessage());
		}
	}

	public void carregarSubCategorias() {
		subCategorias = this.categoriaService.subCategorias(categoriaPai);
	}

	public void pesquisarProduto() {
		this.listaProdutos = this.produtoService.buscarProduto(this.pesquisaProdutoDto);
	}

	public CategoriaService getCategoriaService() {
		return categoriaService;
	}

	public void setCategoriaService(CategoriaService categoriaService) {
		this.categoriaService = categoriaService;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;

		if (this.produto != null) {
			this.categoriaPai = this.produto.getCategoria().getCategoriaPai();
		}
	}

	public Categoria getCategoriaPai() {
		return categoriaPai;
	}

	public void setCategoriaPai(Categoria categoriaPai) {
		this.categoriaPai = categoriaPai;
	}

	public PesquisaProdutoDto getPesquisaProdutoDto() {
		return pesquisaProdutoDto;
	}

	public void setPesquisaProdutoDto(PesquisaProdutoDto pesquisaProdutoDto) {
		this.pesquisaProdutoDto = pesquisaProdutoDto;
	}

	public List<Produto> getListaProdutos() {
		return listaProdutos;
	}

	public void setListaProdutos(List<Produto> listaProdutos) {
		this.listaProdutos = listaProdutos;
	}

	public List<Categoria> getCategoriasRaizes() {
		return categoriasRaizes;
	}

	public void setCategoriasRaizes(List<Categoria> categoriasRaizes) {
		this.categoriasRaizes = categoriasRaizes;
	}

	public List<Categoria> getSubCategorias() {
		return subCategorias;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

}
