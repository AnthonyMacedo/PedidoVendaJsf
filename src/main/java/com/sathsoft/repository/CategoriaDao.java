package com.sathsoft.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sathsoft.model.Categoria;

public class CategoriaDao extends DaoGeneric<Categoria, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public List<Categoria> buscarCategoriasRaizes() {

		TypedQuery<Categoria> query = manager.createQuery("Select c From Categoria c Where c.categoriaPai Is Null", Categoria.class);

		return query.getResultList();
	}

	public List<Categoria> buscarSubCategorias(Categoria categoriaPai) {
		return manager.createQuery("Select c From Categoria c Where c.categoriaPai = :raiz", Categoria.class).setParameter("raiz", categoriaPai).getResultList();
	}
}
