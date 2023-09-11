package com.sathsoft.service;

import java.util.List;

import javax.inject.Inject;

import com.sathsoft.model.Categoria;
import com.sathsoft.repository.CategoriaDao;

public class CategoriaService extends BaseService {

	private static final long serialVersionUID = 1L;

	@Inject
	private CategoriaDao categoriaDao;

	public List<Categoria> buscarTodos() { 
		return categoriaDao.buscarTodos(Categoria.class);
	}

	public Categoria buscarPorId(Long id) {
		return categoriaDao.buscarPorId(Categoria.class, id);
	}
	
	public List<Categoria> categoriasRaizes() {
		return categoriaDao.buscarCategoriasRaizes();
	}
	
	public List<Categoria> subCategorias(Categoria categoriaPai) {
		return categoriaDao.buscarSubCategorias(categoriaPai);
	}
	
}
  