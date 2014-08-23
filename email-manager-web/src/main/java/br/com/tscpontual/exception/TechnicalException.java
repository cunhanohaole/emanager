package br.com.tscpontual.exception;

public class TechnicalException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String title = "Ocorreu um problema tecnico ao executar esta acao...";
	
	public TechnicalException (String message){
		super(message);
	}
	
	public TechnicalException (String message, Throwable cause){
		super(message, cause);
	}

	public String getTitle() {
		return title;
	}

}
