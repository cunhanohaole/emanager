package br.com.tscpontual.exception;

public class RecordNotFoundException extends BusinessException {

	private static final long serialVersionUID = 1L;

	public RecordNotFoundException(String title, String message) {
		super(title, message);
	}

}
