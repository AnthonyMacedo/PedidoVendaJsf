package com.sathsoft.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;

import org.jboss.logging.Logger;

import com.sathsoft.util.jpa.Transactional;

public abstract class DaoGeneric<T, L extends Serializable> implements IBaseRepository<T>, Serializable{

	private static final long serialVersionUID = 1L;

	static final Logger logger = Logger.getLogger(DaoGeneric.class);
	
	@Inject
	private EntityManager manager;
	
	@Override
	@Transactional
	public T salvar(T entity) {
		try {
			return manager.merge(entity);
		} catch (PersistenceException e) {
			logger.error("Falha ao gravar registro. - " + e.getMessage());
			return null;
		}
	}

	@Override
	@Transactional
	public void remover(T entity) {
		try {
			manager.remove(entity);
			manager.flush(); //lança exception se objeto tiver sendo usado por outra tabela.
		} catch (PersistenceException e) {
			logger.error("Falha no excluir o registro. - " + e.getMessage());
			System.out.println(e.getMessage());
		}
	}

	@Override
	public T buscarPorId(Class<T> T, Long id) {
		try {
			return manager.find(T, id);
		} catch (NoResultException e) {
			logger.error("ID não encontrado. - " + e.getMessage());
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<T> buscarTodos(Class<T> classe) {
		try {
			return manager.createQuery("select c from " + classe.getSimpleName() + " c ").getResultList();
		} catch (NoResultException e) {
			logger.warn("Nenhum Registro encontrado.");
			return null;
		}
	}

}
