package com.sathsoft.controller;

import java.io.IOException;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sathsoft.util.jsf.FacesProduces;

@Named
@SessionScoped
public class LoginBean extends BaseManagedBean{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FacesContext facesContext;
	
//	@Inject nao compila quando injeta, foi criado metodo paleativo
//	private HttpServletRequest request;
//	
//	@Inject
//	private HttpServletResponse response; 
	
	private String email;

	public void preRender() {
		if ("true".equals(getRequest().getParameter("invalid"))) {
			super.addErrorMessage("Usuário ou senha inválido!");
		}
	}
	
	public void login() throws ServletException, IOException {
		RequestDispatcher dispatcher = getRequest().getRequestDispatcher("/j_spring_security_check");
		dispatcher.forward(getRequest(), getResponse());
		
		facesContext.responseComplete();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public HttpServletRequest getRequest() {
		return new FacesProduces().getHttpServletRequest();
	}
	
	public HttpServletResponse getResponse() {
		return new FacesProduces().getHttpServletResponse();
	}

}
