package br.com.tscpontual.contacts.model;

public class ImportedEmailAddress {

	private String email;
	
	public ImportedEmailAddress(String email) {
		this.email = email;
	}
	
	public boolean isValid() {
		if (email != null && !email.trim().isEmpty() && email.contains("@"))
			return true;
		return false;
	}
	
	public String format(){
		return email.toLowerCase();
	}
	
	public String getEmail() {
		return email;
	}

}