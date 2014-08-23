package br.com.tscpontual.email.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.activation.MimetypesFileTypeMap;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.tscpontual.email.model.Attachment;
import br.com.tscpontual.email.model.Email;
import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.util.SecurityHelper;

@Repository
public class EmailDAOImpl implements EmailDAO {

	private static final String ATTACHMENTS_DIR = "/home/cunha/apps/emanager/attachments/";
	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Email persistEmail(Email email) {
		return em.merge(email);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Email persistAttachments(List<Attachment> attachments, Email email) throws TechnicalException {
		if(attachments != null && attachments.size() > 0){
			for (Attachment attachment : attachments) {
				attachment.setEmail(email);
				persistAttachmentFile(attachment);
				attachment = em.merge(attachment);
			}
		}
		email.setAttachments(attachments);
		return email;
	}
	
	private void persistAttachmentFile(Attachment attachment) throws TechnicalException {
		//String dirName = Configurations.getProperty("attachments.dir") + attachment.getEmail().getId() + "/";
		String dirName = ATTACHMENTS_DIR + attachment.getEmail().getId() + "/";
		File dir = new File(dirName);
		if(!dir.exists()) dir.mkdirs();
		File file = new File(dirName + attachment.getFileName());
		try {
			OutputStream os = new FileOutputStream(file) ;
			os.write(attachment.getBytes());
			os.close();
		} catch (IOException e) {
			throw new TechnicalException("Error while saving the attachment in the file system: ", e);
		}
	}
	
	@Override
	public List<Attachment> loadAttachmentFiles(Integer emailId) throws TechnicalException {
		List<Attachment> attachments = new ArrayList<Attachment>();
		try {
			String dirName = ATTACHMENTS_DIR + emailId + "/";
			File dir = new File(dirName);
			File[] files = dir.listFiles();
			if(!ArrayUtils.isEmpty(files)) {
				for (File file : files) {
					byte[] fileBytes = FileUtils.readFileToByteArray(file);
					String contentType = new MimetypesFileTypeMap().getContentType(file);
					Attachment attachment = new Attachment();
					attachment.setFileName(file.getName());
					attachment.setBytes(fileBytes);
					attachment.setUserStamp(SecurityHelper.getUserName());
					attachment.setTimeStamp(new Timestamp(new Date().getTime()));
					attachment.setContentType(contentType);
					attachments.add(attachment);
				}
			}
			return attachments;
		} catch (Exception e) {
			throw new TechnicalException("error while loading attachments of email: " + emailId, e);
		}
	}

	@Override
	public List<Email> listSentEmails(String user, int firstResult, int maxResults) {
		//return em.createQuery("select e from Email e where e.userStamp = :user", Email.class).setParameter("user", user).getResultList();
		return em.createQuery("select e from Email e where e.userStamp = :user order by e.createdTimeStamp desc", Email.class).setParameter("user", user).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
	}
	
	@Override
	public int getSentEmailsNumberOfRecords(String user){
		Object result = em.createNativeQuery("select count(*) from email e where e.user_stamp = :user").setParameter("user", user).getSingleResult();
		return ((BigInteger) result).intValue();
	}
	
	@Override
	public Email loadEmail(int emailId){
		return em.createQuery("select e from Email e where e.id = :emailId", Email.class).setParameter("emailId", emailId).getSingleResult();
	}
	
	@Override
	public Email loadEmailDetached(int emailId) {
		Email email = loadEmail(emailId);
		em.detach(email);
		return email;
	}
		
}