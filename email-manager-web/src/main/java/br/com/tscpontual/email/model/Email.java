package br.com.tscpontual.email.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import br.com.tscpontual.contacts.model.AddressGroup;
import br.com.tscpontual.contacts.model.ImportedEmailAddress;


/**
 * The persistent class for the email database table.
 * 
 */
@Entity
@Table(name="email")
public class Email implements Serializable, Comparable<Email> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name="additional_contacts")
	private String additionalContacts;

	@Lob
	@Column(name="email_body")
	@JsonIgnore
	private byte[] body;

	private String subject;
	
	@Column(name="created_timestamp")
	private Timestamp createdTimeStamp;
	
	@Column(name="user_stamp")
	private String userStamp;
	
	private boolean success;
	
	private String status;

	//bi-directional many-to-one association to Attachment
	@OneToMany(mappedBy="email")
	@JsonIgnore
	private List<Attachment> attachments;
	
	//bi-directional many-to-one association to AddressGroup
	@ManyToOne
	@JoinColumn(name="group_id")
	private AddressGroup addressGroup;

	public Email() {
	}
	
	public boolean isStatusSuccess() {
		return EmailStatusEnum.SUCCESS.name().equalsIgnoreCase(status);
	}
	
	public boolean isStatusFailure() {
		return EmailStatusEnum.FAILURE.name().equalsIgnoreCase(status);
	}
	
	public void addAttachment(Attachment attachment){
		if(attachments == null) attachments = new ArrayList<Attachment>();
		attachments.add(attachment);
	}
	
	public void removeAttachment(Integer index){
		if(attachments != null && !attachments.isEmpty())
			attachments.remove(index.intValue());
	}

	public List<ImportedEmailAddress> getAditionalContactsList(){
		List<ImportedEmailAddress> additionalContactsList = new ArrayList<ImportedEmailAddress>();
		if(additionalContacts != null && !additionalContacts.isEmpty() && additionalContacts.contains(";")){
			String [] emailsArray = additionalContacts.split(";");
			for (String email : emailsArray) {
				additionalContactsList.add(new ImportedEmailAddress(email));
			} 
		}
		return additionalContactsList;
	}
	
	public boolean getContainAttachments(){
		return attachments != null && !attachments.isEmpty();
	}
	
	public String getBodyString() {
		if(getBody() != null)
			return new String(getBody());
		return null;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdditionalContacts() {
		return this.additionalContacts;
	}

	public void setAdditionalContacts(String additionalContacts) {
		this.additionalContacts = additionalContacts;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Attachment> getAttachments() {
		return this.attachments;
	}

	public void setAttachments(List<Attachment> attachments) {
		this.attachments = attachments;
	}

	public AddressGroup getAddressGroup() {
		return this.addressGroup;
	}

	public void setAddressGroup(AddressGroup addressGroup) {
		this.addressGroup = addressGroup;
	}

	public Timestamp getCreatedTimeStamp() {
		return createdTimeStamp;
	}

	public void setCreatedTimeStamp(Timestamp createdTimeStamp) {
		this.createdTimeStamp = createdTimeStamp;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getUserStamp() {
		return userStamp;
	}

	public void setUserStamp(String userStamp) {
		this.userStamp = userStamp;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public int compareTo(Email email) {
		return email.getCreatedTimeStamp().compareTo(createdTimeStamp);
	}

}