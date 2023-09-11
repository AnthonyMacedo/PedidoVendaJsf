package com.sathsoft.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.sathsoft.model.Grupo;
import com.sathsoft.model.Usuario;
import com.sathsoft.repository.UsuarioDao;
import com.sathsoft.util.cdi.CDIServiceLocator;

public class AppUsuarioDetailsService implements UserDetailsService {
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		
		UsuarioDao usuarioDao = CDIServiceLocator.getBean(UsuarioDao.class);
		Usuario usuario = usuarioDao.buscarPorEmail(email);
		UsuarioSistema user = null;
		
		if (usuario != null) {
			 user = new UsuarioSistema(usuario, getGrupos(usuario));
		}
		
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Usuario usuario) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		for (Grupo grupo: usuario.getGrupos()) {
			authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
		}
		
		return authorities;
	}

}
