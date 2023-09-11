package com.sathsoft.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.sathsoft.model.Usuario;
import com.sathsoft.service.UsuarioService;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	@Inject
	private UsuarioService service;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		try {
			Long id = Long.parseLong(value);
			Usuario retorno = service.buscarPorId(id);
			return retorno;
		} catch (RuntimeException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		try {
			Usuario retorno = (Usuario) value;
			Long codigo = retorno.getId();
			return codigo.toString();

		} catch (Exception e) {
			return null;
		}
	}

}
