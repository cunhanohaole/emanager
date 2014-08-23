package br.com.tscpontual.exception;

public class MailjetParseExeption extends TechnicalException {

	private static final long serialVersionUID = 1L;

	public MailjetParseExeption(Throwable t) {
		super("Error while parsing mailjet XML!", t);
	}
	
}