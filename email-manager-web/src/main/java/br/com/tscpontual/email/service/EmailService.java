package br.com.tscpontual.email.service;

import br.com.tscpontual.email.model.Email;
import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.user.model.SenderConfig;

public interface EmailService {

	void sendEmail(Email email, SenderConfig user) throws TechnicalException;
	
}