package com.sathsoft.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.sathsoft.model.Cliente;
import com.sathsoft.service.ClienteService;

@FacesConverter(forClass = Cliente.class)
public class ClienteConverter implements Converter {

	@Inject
	private ClienteService service;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		try {
			Long id = Long.parseLong(value);
			Cliente retorno = service.buscarPorId(id);
			return retorno;
		} catch (RuntimeException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		try {
			Cliente retorno = (Cliente) value;
			Long codigo = retorno.getId();
			return codigo.toString();

		} catch (Exception e) {
			return null;
		}
	}

}
