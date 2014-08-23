package br.com.tscpontual.email.manager;

import java.util.List;

import br.com.tscpontual.email.model.Attachment;
import br.com.tscpontual.email.model.Email;
import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.user.model.SenderConfig;


public interface EMailManager {

    void sendEmail(Integer senderId, Integer groupId, String subject, String body, String additionalEmails, List<Attachment> attachments) throws TechnicalException;

    List<Email> listSentEmails(Integer senderConfigId, int page, int numberOfRowsPerPage);

	Email loadEmail(int emailId);

	int getNumberOfPagesForSentEmails(int numberOfRowsPerPage);

	void sendEmail(Email email, SenderConfig senderConfig);

    void forwardEmail(Integer senderId, Integer emailId, Integer newGroupId) throws TechnicalException;

	void checkInvalidSentEmails() throws TechnicalException;

	List<Attachment> loadAttachmentFiles(Integer emailId)
			throws TechnicalException;
	
}
