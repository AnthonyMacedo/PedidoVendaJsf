package com.sathsoft.service;

import java.util.List;

import javax.inject.Inject;

import com.sathsoft.model.Cliente;
import com.sathsoft.repository.ClienteDao;

public class ClienteService extends BaseService {

	private static final long serialVersionUID = 1L;
	
	private static final int MAX_RESULT = 15;

	@Inject
	private ClienteDao clientedao;
	
	public List<Cliente> buscarClientePorNome(String nome){
		return clientedao.buscarPorNome(nome, MAX_RESULT);
	}

	public Cliente buscarPorId(Long id) {
		return clientedao.buscarPorId(Cliente.class, id);
	}
	
	public List<Cliente> buscarTodos() {
		return clientedao.buscarTodos(Cliente.class);
	}
	
}
