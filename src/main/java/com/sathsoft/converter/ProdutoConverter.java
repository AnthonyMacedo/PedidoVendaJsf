package com.sathsoft.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import com.sathsoft.model.Produto;
import com.sathsoft.service.ProdutoService;

@FacesConverter(forClass = Produto.class)
public class ProdutoConverter implements Converter {

	@Inject
	private ProdutoService service;

	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {

		try {
			Long id = Long.parseLong(value);
			Produto retorno = service.buscarPorId(id);
			return retorno;
		} catch (RuntimeException e) {
			return null;
		}
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {

		try {
			Produto retorno = (Produto) value;
			Long codigo = retorno.getId();
			return codigo.toString();

		} catch (Exception e) {
			return null;
		}
	}

}
