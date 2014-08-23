package br.com.tscpontual.email.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tscpontual.contacts.dao.ContactsDAO;
import br.com.tscpontual.contacts.model.Contact;
import br.com.tscpontual.contacts.model.ImportedEmailAddress;
import br.com.tscpontual.email.dao.EmailDAO;
import br.com.tscpontual.email.model.Attachment;
import br.com.tscpontual.email.model.Email;
import br.com.tscpontual.email.model.EmailStatusEnum;
import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.user.model.SenderConfig;
import br.com.tscpontual.util.AppUtils;
import br.com.tscpontual.util.Messages;

@Service
public class EmailServiceImpl implements EmailService {

	private static Logger log = Logger.getLogger(EmailServiceImpl.class);
	
	@Autowired
	private ContactsDAO contactsDAO;
	
	@Autowired
	private EmailDAO emailDAO;
	
	private int numberOfEmailsPerTime = 30;
	
	@Override
	public void sendEmail(Email email, SenderConfig senderConfig) throws TechnicalException {
		//String attachmentsDir = Configurations.getProperty("attachments.dir");
		String attachmentsDir = "/home/cunha/apps/emanager/attachments/";
		List<Contact> emailAddresses = email.getAddressGroup().getEmailAddresses();
		List<ImportedEmailAddress> aditionalContacts = email.getAditionalContactsList();
		List<List<String>> splittedEmailList = getSplittedEmailList(emailAddresses, aditionalContacts);
		log.info("Starting sending e-mails from: " + senderConfig.getFrom());
		int count = 1;
		for (List<String> emailAddressList : splittedEmailList) {
			try {
				log.info("Sending e-mails from " + senderConfig.getFrom()  + ": Part " + count);
				HtmlEmail htmlEmail = new HtmlEmail();
				configMailAuth(htmlEmail, senderConfig);
				configMailHostName(htmlEmail, senderConfig);
				configMailSession(htmlEmail, senderConfig);
				
				addBcc(htmlEmail, emailAddressList);
				
				htmlEmail.setFrom(senderConfig.getFrom());
				htmlEmail.setSubject(email.getSubject());
				
				
				String body = email.getBodyString();
				if(body != null){
					//log.debug("Message: \n" + body);
					body = formatHtmlMessage(body);
					body = "<html><head><META HTTP-EQUIV=\"Content-Type\" CONTENT=\"text/html; charset=UTF-8\"></head><body>" + body + "</body></html>";
					log.debug(System.getProperty("file.encoding"));
					log.debug("Message: \n" + body);
					htmlEmail.setHtmlMsg(body);
				}
				
				List<Attachment> attachments = email.getAttachments();
				if(attachments != null){
					for (Attachment attachment : attachments) {
						EmailAttachment emailAttachment = new EmailAttachment();
						emailAttachment.setPath(attachmentsDir + email.getId() + "/" + attachment.getFileName());
						emailAttachment.setDisposition(EmailAttachment.ATTACHMENT);
						emailAttachment.setName(attachment.getFileName());
						htmlEmail.attach(emailAttachment);
					}
				}
				htmlEmail.send();
				log.info("Sent e-mails from " + senderConfig.getFrom()  + ": Part " + count);
				count++;
			} catch (EmailException e) {
				throw new TechnicalException(Messages.ERROR_WHILE_SENDING_TO_SOMEONE, e);
			} finally {
				//TODO setar status do email para erro e quando criar setar para pendente
			}
		}
		email.setSuccess(true);
		email.setStatus(EmailStatusEnum.SUCCESS.name());
		emailDAO.persistEmail(email);
		log.info("Emails successfuly sent from " + senderConfig.getFrom());
	}
	
	public String formatHtmlMessage(String body){
		body = StringEscapeUtils.escapeHtml(body);
		body = body.replaceAll("&lt;", "<");
		body = body.replaceAll("&gt;", ">");
		body = body.replaceAll("&quot;", "\"");
		body = body.replaceAll("\\n", "");
		body = body.replaceAll("\u00a0","");
		body = body.replaceAll("&nbsp;","");
		body = body.replaceAll("&amp;nbsp;","");
		return body;
	}
	
	private void addBcc(MultiPartEmail multiPartEmail, List<String> emailAddresses) throws EmailException {
		for (String email : emailAddresses) {
			multiPartEmail.addBcc(email);
		}
	}
	
	
	
	private List<List<String>> getSplittedEmailList(List<Contact> emailAddresses, List<ImportedEmailAddress> aditionalContacts){
		List<List<String>> lists = new ArrayList<List<String>>();
		List<String> originalList = getEmailsAddresses(emailAddresses, aditionalContacts);
		for (int i = 0; i < originalList.size(); i += numberOfEmailsPerTime) {
	        lists.add(originalList.subList(i, Math.min(i + numberOfEmailsPerTime, originalList.size())));
	    }
		return lists;
	}
	
	private List<String> getEmailsAddresses(List<Contact> emailAddresses, List<ImportedEmailAddress> aditionalContacts){
		List<String> list = new ArrayList<String>(emailAddresses.size() + aditionalContacts.size());
		for (int i = 0; i < emailAddresses.size(); i++) {
			Contact contact = emailAddresses.get(i);
			String email = contact.getAddress();
			if(AppUtils.isValidEmail(email) && !contact.isInvalid()) {
				list.add(email);
			}
			else if(!AppUtils.isValidEmail(email)){
				log.info("Invalid email format on the database. Setting as invalid. Email: " + email);
				contactsDAO.setContactAsInvalid(email, "wrong format");
			}
		}
		for (int i = 0; i < aditionalContacts.size(); i++) {
			String email = aditionalContacts.get(i).getEmail();
			if(AppUtils.isValidEmail(email))
				list.add(email);
			else log.info("Invalid aditional email: " + email);
		}
		return list;
	}
	
	private void configMailAuth(org.apache.commons.mail.Email email, SenderConfig senderConfig) throws TechnicalException {
		String username = senderConfig.getUsername();
		String passoword = senderConfig.getPassword();
		if(username != null && passoword != null){
			email.setAuthenticator(new DefaultAuthenticator(username, passoword));
		}
		else {
			throw new TechnicalException("O parametro username ou password nao foi encontrado no arquivo de configuracao!");
		}
	}
	
	private void configMailHostName(org.apache.commons.mail.Email email, SenderConfig senderConfig) throws TechnicalException {
		String hostName = senderConfig.getHostname();
		if(hostName != null){
			email.setHostName(hostName);
		}
		else {
			throw new TechnicalException("O parametro hostname nao foi encontrado no arquivo de configuracao!");
		}
	}
	
	private void configMailSession(org.apache.commons.mail.Email email, SenderConfig senderConfig) throws TechnicalException {
		try {
			boolean auth = senderConfig.getMailSmtpAuth();
			if(auth){
				email.getMailSession().getProperties().put("mail.smtp.auth", String.valueOf(auth));
			}
			String smtpPort = senderConfig.getMailSmtpPort();
			if(smtpPort != null){
				email.getMailSession().getProperties().put("mail.smtp.port", smtpPort);
			}
			String socketPort = senderConfig.getMailSmtpSocketFactoryPort();
			if(socketPort != null){
				email.getMailSession().getProperties().put("mail.smtp.socketFactory.port", socketPort);
			}
			String socketFactoryClass = senderConfig.getMailSmtpSocketFactoryClass();
			if(socketFactoryClass != null){
				email.getMailSession().getProperties().put("mail.smtp.socketFactory.class",  socketFactoryClass);
			}
			String fallBack = senderConfig.getMailSmtpSocketFactoryFallback();
			if(fallBack != null){
				email.getMailSession().getProperties().put("mail.smtp.socketFactory.fallback", fallBack);
			}
			boolean startTtlsEneble = senderConfig.getMailSmtpStarttlsEnable();
			if(startTtlsEneble){
				email.getMailSession().getProperties().put("mail.smtp.starttls.enable", String.valueOf(startTtlsEneble));
			}
		} catch (EmailException e) {
			throw new TechnicalException(e.getMessage(), e);
		}
	}

}
