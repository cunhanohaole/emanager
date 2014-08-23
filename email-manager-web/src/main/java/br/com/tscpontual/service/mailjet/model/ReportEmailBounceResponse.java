package br.com.tscpontual.service.mailjet.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "xml")
public class ReportEmailBounceResponse {

	@XmlElement(name = "status", required = true)
	private String status;
	
	@XmlElement(name = "bounces", required = true)
	private EmailBounces emailBounces;

	public String getStatus() {
		return status;
	}

	public EmailBounces getEmailBounces() {
		return emailBounces;
	}
	
}
