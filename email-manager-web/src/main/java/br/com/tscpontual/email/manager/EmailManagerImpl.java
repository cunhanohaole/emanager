package br.com.tscpontual.email.manager;

import br.com.tscpontual.contacts.dao.ContactsDAO;
import br.com.tscpontual.contacts.model.AddressGroup;
import br.com.tscpontual.email.dao.EmailDAO;
import br.com.tscpontual.email.dao.SenderConfigDAO;
import br.com.tscpontual.email.model.Attachment;
import br.com.tscpontual.email.model.Email;
import br.com.tscpontual.email.model.EmailStatusEnum;
import br.com.tscpontual.email.service.EmailRunnable;
import br.com.tscpontual.email.service.EmailService;
import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.service.MailProviderAPIClient;
import br.com.tscpontual.service.mailjet.model.EmailBounceItem;
import br.com.tscpontual.service.mailjet.model.EmailBounces;
import br.com.tscpontual.service.mailjet.model.ReportEmailBounceResponse;
import br.com.tscpontual.user.model.SenderConfig;
import br.com.tscpontual.util.SecurityHelper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EmailManagerImpl implements EMailManager {

	private static Logger log = Logger.getLogger(EmailManagerImpl.class);
	
	@Autowired
	private EmailDAO emailDAO;
	
	@Autowired
	private ContactsDAO contactsDAO;

    @Autowired
    private SenderConfigDAO senderConfigDAO;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private EmailRunnable emailRunnable;
	
	@Autowired
	private MailProviderAPIClient mailProviderAPIClient;

	@Override
	public void sendEmail(Integer senderId, Integer groupId, String subject, String body, String additionalEmails, List<Attachment> attachments) throws TechnicalException {
		String loggedUserName = SecurityHelper.getUserName();
		SenderConfig senderConfig = senderConfigDAO.getSenderConfig(senderId);
		if(senderConfig != null){
			Email email = new Email();
			email.setAddressGroup(new AddressGroup(groupId));
			email.setSubject(subject);
			email.setBody(body.getBytes());
			email.setAdditionalContacts(additionalEmails);
			email.setCreatedTimeStamp(new Timestamp(new Date().getTime()));
			email.setUserStamp(loggedUserName);
			//TODO remove this property - success - repalced by status
			email.setSuccess(false);
			email.setStatus(EmailStatusEnum.PROCESSING.name());
			email = emailDAO.persistEmail(email);
			email = emailDAO.persistAttachments(attachments, email);
			sendEmail(email, senderConfig);
		}
		else throw new TechnicalException("This user (" + loggedUserName + ") is not set up as an e-mail sender! It should be used only for administration tasks!");
	}
	
	@Override
	public List<Attachment> loadAttachmentFiles(Integer emailId) throws TechnicalException{
		return emailDAO.loadAttachmentFiles(emailId);
	}
	
	@Override
	public void sendEmail(Email email, SenderConfig senderConfig){
		emailRunnable.init(email, senderConfig);
		Thread thread = new Thread(emailRunnable);
		thread.start();
	}

	@Override
	public List<Email> listSentEmails(Integer senderConfigId, int page, int numberOfRowsPerPage) {
		int fistResult = numberOfRowsPerPage * (page - 1);
		return emailDAO.listSentEmails(SecurityHelper.getUserName(), senderConfigId, fistResult, numberOfRowsPerPage);
	}
	
	@Override
	public Email loadEmail(int emailId){
		return emailDAO.loadEmail(emailId);
	}
	
	@Override
	public void forwardEmail(Integer senderId, Integer emailId, Integer newGroupId) throws TechnicalException {
		String loggedUserName = SecurityHelper.getUserName();
		SenderConfig senderConfig = senderConfigDAO.getSenderConfig(senderId);
		Email email = emailDAO.loadEmailDetached(emailId);
		if(senderConfig != null){
			email.setAddressGroup(new AddressGroup(newGroupId));
			email.setCreatedTimeStamp(new Timestamp(new Date().getTime()));
			email.setUserStamp(loggedUserName);
			email = emailDAO.persistEmail(email);
			sendEmail(email, senderConfig);
		}
		else throw new TechnicalException("This user (" + loggedUserName + ") is not set up as an e-mail sender! It should be used only for administration tasks!!!");
	}
	
	@Override
	public int getNumberOfPagesForSentEmails(int numberOfRowsPerPage){
		int rowCount = emailDAO.getSentEmailsNumberOfRecords(SecurityHelper.getUserName());
		int numOfPages = 0;
		int restPart = rowCount % numberOfRowsPerPage;
		int intPart = rowCount / numberOfRowsPerPage;
		if(restPart > 0) numOfPages = intPart + 1;
		else numOfPages = intPart;
		return numOfPages;
	}
	
	@Override
	public void checkInvalidSentEmails() throws TechnicalException {
		log.info("checking invalid emails addresses...");
		Calendar cFrom = Calendar.getInstance();
		cFrom.setTime(new Date());
		cFrom.add(Calendar.DAY_OF_MONTH, -1);
		cFrom.set(Calendar.HOUR, 0);// Set the from date to one day before today at 0 hour
		Calendar cTo = Calendar.getInstance();
		cTo.setTime(new Date());
		cTo.set(Calendar.HOUR, 0);// Set the to date today at 0 hour
		ReportEmailBounceResponse response = mailProviderAPIClient.listEmailBounces(5000, cFrom.getTime(), cTo.getTime());
		boolean invalidEmailsFound = false;
		if(response != null) {
			log.info("response received from list email bounces.");
			EmailBounces emailBounces = response.getEmailBounces();
			if(emailBounces != null) {
				List<EmailBounceItem> items = emailBounces.getItems();
				if(CollectionUtils.isNotEmpty(items)) {
					invalidEmailsFound = true;
					log.info("list email bounces returned " + items.size() + " invalid emails addresses.");
					for (EmailBounceItem emailBounceItem : items) {
						String address = emailBounceItem.getEmail();
						if(StringUtils.isNotEmpty(address)) {
							contactsDAO.setContactAsInvalid(address, emailBounceItem.getError());
						}
					}
				}
			}
		}
		if(!invalidEmailsFound) {
			log.info("list email bounces didnt return any invalid email for period " + cFrom.getTime() + " to " + cTo.getTime());
		}
	}
	
}
