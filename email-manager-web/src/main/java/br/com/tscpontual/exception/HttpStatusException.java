package br.com.tscpontual.exception;

public class HttpStatusException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private String httpStatusCode;

	public HttpStatusException(String httpStatusCode) {
		super("Http status code != 200. Status code = " + httpStatusCode);
		this.httpStatusCode = httpStatusCode;
	}

	public String getHttpStatusCode() {
		return httpStatusCode;
	}

}
