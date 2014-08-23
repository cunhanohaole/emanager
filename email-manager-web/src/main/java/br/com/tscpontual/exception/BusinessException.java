package br.com.tscpontual.exception;

public class BusinessException extends Exception {

	private static final long serialVersionUID = 1L;

	private String title;
	
	public BusinessException (String title, String message){
		super(message);
		this.title = title;
	}
	
	public BusinessException (String title, String message, Throwable cause){
		super(message, cause);
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
}
