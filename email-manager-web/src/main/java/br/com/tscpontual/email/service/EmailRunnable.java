package br.com.tscpontual.email.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import br.com.tscpontual.email.model.Email;
import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.user.model.SenderConfig;

@Component
@Scope("prototype")
public class EmailRunnable implements Runnable {

	private static Logger log = Logger.getLogger(EmailRunnable.class);
	
	@Autowired
	private EmailService emailService;
	
	private Email email;
	
	private SenderConfig senderConfig;
	
	public void init(Email email, SenderConfig senderConfig){
		this.email = email;
		this.senderConfig = senderConfig;
	}
	
	@Override
	public void run() {
		try {
			emailService.sendEmail(email, senderConfig);
		} catch (TechnicalException e) {
			log.error("Error while sending email from " + senderConfig.getFrom(), e);
		}
	}

}
