package com.sathsoft.repository;

import java.util.List;

public interface IBaseRepository<T> {
	
	T salvar(T entity);
	
	void remover(T entity);
	
	T buscarPorId(Class<T> classe, Long id);
	
	List<T> buscarTodos(Class<T> classe);
	
}
