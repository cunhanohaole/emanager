package br.com.tscpontual.email.dao;

import java.util.List;

import br.com.tscpontual.email.model.Attachment;
import br.com.tscpontual.email.model.Email;
import br.com.tscpontual.exception.TechnicalException;


public interface EmailDAO {

	Email persistEmail(Email email);
	
	Email persistAttachments(List<Attachment> attachments, Email email) throws TechnicalException;
	
	public List<Email> listSentEmails(String user, int firstResult, int maxResults);

	Email loadEmail(int emailId);

	int getSentEmailsNumberOfRecords(String user);

	Email loadEmailDetached(int emailId);

	List<Attachment> loadAttachmentFiles(Integer emailId)
			throws TechnicalException;
	
}
