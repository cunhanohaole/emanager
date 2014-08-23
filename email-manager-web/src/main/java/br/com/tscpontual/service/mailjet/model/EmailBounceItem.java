package br.com.tscpontual.service.mailjet.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "item")
public class EmailBounceItem {

	@XmlElement(name = "email", required = true)
	private String email;
	
	@XmlElement(name = "mj_campaign_id", required = false)
	private String campaignId;
	
	@XmlElement(name = "mj_contact_id", required = false)
	private String contactId;
	
	@XmlElement(name = "blocked", required = true)
	private Integer blocked;
	
	@XmlElement(name = "hard_bounce", required = true)
	private Integer hardBounce;
	
	@XmlElement(name = "date_ts", required = false)
	private String date;
	
	@XmlElement(name = "error_related_to", required = false)
	private String errorCause;
	
	@XmlElement(name = "error", required = false)
	private String error;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(String campaignId) {
		this.campaignId = campaignId;
	}

	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public Integer getBlocked() {
		return blocked;
	}

	public void setBlocked(Integer blocked) {
		this.blocked = blocked;
	}

	public Integer getHardBounce() {
		return hardBounce;
	}

	public void setHardBounce(Integer hardBounce) {
		this.hardBounce = hardBounce;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getErrorCause() {
		return errorCause;
	}

	public void setErrorCause(String errorCause) {
		this.errorCause = errorCause;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}	
	
}
