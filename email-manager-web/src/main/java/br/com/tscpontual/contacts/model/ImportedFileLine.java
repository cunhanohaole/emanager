package br.com.tscpontual.contacts.model;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class ImportedFileLine {

	private String text;
	
	public ImportedFileLine(String text) {
		this.text = text;
	}
	
	/**
	 * Verifica se uma determinada linha e uma linha valida
	 * 
	 * @param line
	 *            A linha a ser validada
	 * @return true se for valida e false caso contrario
	 */
	public boolean isValid() {
		if (text != null && !text.trim().isEmpty()) {
			return true;
		}
		return false;
	}
	
	/**
	 * Verifica se uma linha e uma linha em que os emails estao separados por
	 * ponto e virgula
	 * 
	 * @param line
	 *            A linha a ser verificada
	 * @return true se for, false caso contrario
	 */
	public boolean isCommaSeparated() {
		if (text.contains(";"))
			return true;
		return false;
	}
	
	public boolean hasMoreThanOneEmail(){
		if(isCommaSeparated() || hasMoreThanOneAt()) return true;
		return false;
	}
	
	public boolean hasMoreThanOneAt(){
		return hasMoreThanOneAt(text);
	}
	
	private boolean hasMoreThanOneAt(String text){
		if(StringUtils.countMatches(text, "@") > 1){
			return true;
		}
		return false;
	}
	
	public List<ImportedEmailAddress> breakIntoEmails(){
		List<ImportedEmailAddress> emailList = new ArrayList<ImportedEmailAddress>();
		text = text.replace("<", ";");
		text = text.replace(">", ";");
		text = text.replace("'", "");
		if(isCommaSeparated()){
			String [] commaSeparatedEmails = text.split(";");
			for (String preEmail : commaSeparatedEmails) {
				if(hasMoreThanOneAt(preEmail)){
					List<ImportedEmailAddress> ems = separateEmailsWithTwoAt(preEmail);
					emailList.addAll(ems);
				}
				else {
					emailList.add(new ImportedEmailAddress(preEmail));
				}
			}
		}
		else {
			if(hasMoreThanOneAt()){
				List<ImportedEmailAddress> ems = separateEmailsWithTwoAt(text);
				emailList.addAll(ems);
			}
			else {
				emailList.add(new ImportedEmailAddress(text));
			}
		}
		return emailList;
	}
	
	private List<ImportedEmailAddress> separateEmailsWithTwoAt(String preEmail){
		List<ImportedEmailAddress> emailList = new ArrayList<ImportedEmailAddress>();
		int firstDotLocation = preEmail.indexOf(".");
		int whereNextEmailBegins = 0;
		if(preEmail.charAt(firstDotLocation + 3) == '.'){
			whereNextEmailBegins = firstDotLocation + 6;
		}
		else if(preEmail.charAt(firstDotLocation + 4) == '.'){
			whereNextEmailBegins = firstDotLocation + 7;
		}
		else {
			whereNextEmailBegins = firstDotLocation + 4;
		}
		String firstEmail = preEmail.substring(0, whereNextEmailBegins);
		String secondEmail = preEmail.substring(whereNextEmailBegins, preEmail.length());
		ImportedEmailAddress e1 = new ImportedEmailAddress(firstEmail);
		ImportedEmailAddress e2 = new ImportedEmailAddress(secondEmail);
		emailList.add(e1);
		emailList.add(e2);
		return emailList;
	}
	
	public boolean isGroup(String groupMarker){
		if (text.startsWith(groupMarker)) {
			return true;
		}
		return false;
	}
	
	public String getGroupName(String groupMarker){
		return text.substring(groupMarker.length());
	}
	
	public void removeUnwantedChars(){
		text = text.replace("<", ";");
		text = text.replace(">", ";");
		text = text.replace("'", "");
		text = text.replace(" ", "");
		text = Normalizer.normalize(text, Normalizer.Form.NFD);
		text = text.replaceAll("[^\\p{ASCII}]", "");
	}

	public String getText() {
		return text;
	}
	
}
