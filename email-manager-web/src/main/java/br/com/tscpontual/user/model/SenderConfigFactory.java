package br.com.tscpontual.user.model;

public interface SenderConfigFactory {

	SenderConfig create(User user, String from);
	
}
