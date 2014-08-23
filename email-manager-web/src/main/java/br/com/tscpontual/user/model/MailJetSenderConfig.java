package br.com.tscpontual.user.model;

import org.springframework.stereotype.Component;

@Component
public class MailJetSenderConfig implements SenderConfigFactory {

	@Override
	public SenderConfig create(String from) {
		return new SenderConfig(getUsername(), getPassword(), getHostName(), from, getMailSmtpPort(), getMailSmtpAuth());
	}
	
	private String getUsername(){
		return "49583900035b28567726201610008da2";
	}
	
	private String getPassword() {
		return "af0e435b6a7ce215f5f46b81107b7e44";
	}

	private String getHostName() {
		return "in.mailjet.com";
	}
	
	private String getMailSmtpPort() {
		return "587";
	}
	
	private boolean getMailSmtpAuth() {
		return true;
	}
	
}
