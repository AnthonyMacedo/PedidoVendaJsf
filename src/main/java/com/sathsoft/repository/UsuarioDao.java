package com.sathsoft.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.sathsoft.model.Usuario;

public class UsuarioDao extends DaoGeneric<Usuario, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public List<Usuario> buscarPorNome(String nome, int maxResult) {

		TypedQuery<Usuario> query = manager.createNamedQuery("From Usuario u where UPPER(u.nome) like %UPPER(:nome)%",
				Usuario.class);

		query.setMaxResults(maxResult);

		return query.getResultList();
	}

	public Usuario buscarPorEmail(String email) {
		Usuario usuario = null;

		try {
			usuario = manager.createQuery("From Usuario u where UPPER(u.email) = :email", Usuario.class)
					.setParameter("email", email.toUpperCase()).getSingleResult();

		} catch (NoResultException e) {
			logger.warn(e.getMessage());
		}
		
		return usuario;
	}

}
