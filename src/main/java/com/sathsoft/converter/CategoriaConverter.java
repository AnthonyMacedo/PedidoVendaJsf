package com.sathsoft.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.sathsoft.model.Categoria;
import com.sathsoft.service.CategoriaService;

@FacesConverter(forClass = Categoria.class)
public class CategoriaConverter implements Converter {

	@Inject
	private CategoriaService service;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		try {
			Long id = Long.parseLong(value);
			Categoria retorno = service.buscarPorId(id);
			return retorno;
		} catch (RuntimeException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		try {
			Categoria retorno = (Categoria) value;
			Long codigo = retorno.getId();
			return codigo.toString();

		} catch (Exception e) {
			return null;
		}
	}

}
