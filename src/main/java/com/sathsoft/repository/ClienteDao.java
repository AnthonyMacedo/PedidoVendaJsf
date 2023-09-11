package com.sathsoft.repository;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.sathsoft.model.Cliente;

public class ClienteDao extends DaoGeneric<Cliente, Long> {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;
	
	
	public List<Cliente> buscarPorNome(String nome, int maxResult) {
		
		String addFiltro = "";
		
		if (nome != null && !nome.isEmpty()) {
			addFiltro += "and Upper(c.nome) like Upper(:nome) ";
		}
		
		TypedQuery<Cliente> query =  manager.createQuery("select c From Cliente c "
				+ "where c.id is not null "
				+ addFiltro
				+ "order by c.id desc", Cliente.class);
		
		if (addFiltro.contains(":nome")) {
			query.setParameter("nome", "%" + nome + "%");
		}
		
		query.setMaxResults(maxResult);
		
			return query.getResultList();
	}
}
