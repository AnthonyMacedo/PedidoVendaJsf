package com.sathsoft.util.mail;

import java.io.IOException;
import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import com.outjected.email.api.SessionConfig;
import com.outjected.email.impl.SimpleMailConfig;

public class MailConfigProducer {
	
	@Produces
	@ApplicationScoped
	public SessionConfig getMailConfig() throws IOException {
		Properties propertie = new Properties();
		propertie.load(getClass().getResourceAsStream("/mail.properties"));
		
		SimpleMailConfig config = new SimpleMailConfig();
		config.setServerHost(propertie.getProperty("mail.server.host"));
		config.setServerPort(Integer.parseInt(propertie.getProperty("mail.server.port")));
		config.setEnableSsl(Boolean.parseBoolean(propertie.getProperty("mail.enable.ssl")));
		config.setAuth(Boolean.parseBoolean(propertie.getProperty("mail.auth")));
		config.setUsername(propertie.getProperty("mail.username"));
		config.setPassword(propertie.getProperty("mail.password"));
		
		return config;
	}
}
