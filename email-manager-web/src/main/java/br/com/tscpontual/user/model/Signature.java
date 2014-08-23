package br.com.tscpontual.user.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

@Entity
@Table(name="signature")
public class Signature implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "id_signature")
	private Integer idSignature;
	
	private String title;

	@Column(name = "html_signature")
	private String htmlSignature;
	
	@ManyToOne
	@JoinColumn(name="username")
	@JsonIgnore
	private User user;

	public Signature() {
	}

	public Signature(String title, String htmlSignature, User user) {
		this.title = title;
		this.htmlSignature = htmlSignature;
		this.user = user;
	}

	public Integer getIdSignature() {
		return idSignature;
	}

	public void setIdSignature(Integer idSignature) {
		this.idSignature = idSignature;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHtmlSignature() {
		return htmlSignature;
	}

	public void setHtmlSignature(String htmlSignature) {
		this.htmlSignature = htmlSignature;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
