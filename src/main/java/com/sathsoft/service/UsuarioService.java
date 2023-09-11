package com.sathsoft.service;

import java.util.List;

import javax.inject.Inject;

import com.sathsoft.model.Usuario;
import com.sathsoft.repository.UsuarioDao;

public class UsuarioService extends BaseService {

	private static final long serialVersionUID = 1L;
	
	private static final int MAX_RESULT = 15;

	@Inject
	private UsuarioDao usuarioDao;
	
	
	public List<Usuario> buscarTodos() {
		return usuarioDao.buscarTodos(Usuario.class);
	}
	
	public List<Usuario> buscarPorNome(String nomeUsuario) {
		return usuarioDao.buscarPorNome(nomeUsuario, MAX_RESULT);
	}

	public Usuario buscarPorId(Long id) {
		return usuarioDao.buscarPorId(Usuario.class, id);
	}
}
