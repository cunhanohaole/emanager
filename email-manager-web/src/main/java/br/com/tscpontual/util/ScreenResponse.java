package br.com.tscpontual.util;

public class ScreenResponse {

	private boolean error;
	
	private boolean friendlyError;
	
	private String title;
	
	private String message;
	
	public ScreenResponse(boolean error, boolean isFriendlyError,
			String title, String message) {
		this.error = error;
		this.friendlyError = isFriendlyError;
		this.title = title;
		this.message = message;
	}

    public ScreenResponse(String title, String message) {
        this.error = false;
        this.friendlyError = false;
        this.title = title;
        this.message = message;
    }

	public boolean isError() {
		return error;
	}

	public boolean isFriendlyError() {
		return friendlyError;
	}

	public String getTitle() {
		return title;
	}

	public String getMessage() {
		return message;
	}

    public String getNotifyType() {
        return isError() ? "error" : "success";
    }
	
}
