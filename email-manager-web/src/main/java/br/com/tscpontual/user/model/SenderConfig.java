package br.com.tscpontual.user.model;

import java.io.Serializable;
import javax.persistence.*;

import org.codehaus.jackson.annotate.JsonIgnore;

import java.util.List;


/**
 * The persistent class for the sender_config database table.
 * 
 */
@Entity
@Table(name="sender_config")
public class SenderConfig implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	@Column(name="mail_from")
	private String from;

	private String hostname;

	@Column(name="mail_smtp_auth")
	private boolean mailSmtpAuth;

	@Column(name="mail_smtp_port")
	private String mailSmtpPort;

	@Column(name="mail_smtp_socket_factory_class")
	private String mailSmtpSocketFactoryClass;

	@Column(name="mail_smtp_socket_factory_fallback")
	private String mailSmtpSocketFactoryFallback;

	@Column(name="mail_smtp_socket_factory_port")
	private String mailSmtpSocketFactoryPort;

	@Column(name="mail_smtp_starttls_enable")
	private boolean mailSmtpStarttlsEnable;

	private String password;

	private String username;

	//bi-directional many-to-one association to User
	@JsonIgnore
	@OneToMany(mappedBy="senderConfig")
	private List<User> users;

	public SenderConfig() {
	}

	public SenderConfig(String username, String password, String hostname, String from, String mailSmtpPort, boolean mailSmtpAuth) {
		this.hostname = hostname;
		this.password = password;
		this.username = username;
		this.from = from;
		this.mailSmtpAuth = mailSmtpAuth;
		this.mailSmtpPort = mailSmtpPort;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFrom() {
		return this.from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getHostname() {
		return this.hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public boolean getMailSmtpAuth() {
		return this.mailSmtpAuth;
	}

	public void setMailSmtpAuth(boolean mailSmtpAuth) {
		this.mailSmtpAuth = mailSmtpAuth;
	}

	public String getMailSmtpPort() {
		return this.mailSmtpPort;
	}

	public void setMailSmtpPort(String mailSmtpPort) {
		this.mailSmtpPort = mailSmtpPort;
	}

	public String getMailSmtpSocketFactoryClass() {
		return this.mailSmtpSocketFactoryClass;
	}

	public void setMailSmtpSocketFactoryClass(String mailSmtpSocketFactoryClass) {
		this.mailSmtpSocketFactoryClass = mailSmtpSocketFactoryClass;
	}

	public String getMailSmtpSocketFactoryFallback() {
		return this.mailSmtpSocketFactoryFallback;
	}

	public void setMailSmtpSocketFactoryFallback(String mailSmtpSocketFactoryFallback) {
		this.mailSmtpSocketFactoryFallback = mailSmtpSocketFactoryFallback;
	}

	public String getMailSmtpSocketFactoryPort() {
		return this.mailSmtpSocketFactoryPort;
	}

	public void setMailSmtpSocketFactoryPort(String mailSmtpSocketFactoryPort) {
		this.mailSmtpSocketFactoryPort = mailSmtpSocketFactoryPort;
	}

	public boolean getMailSmtpStarttlsEnable() {
		return this.mailSmtpStarttlsEnable;
	}

	public void setMailSmtpStarttlsEnable(boolean mailSmtpStarttlsEnable) {
		this.mailSmtpStarttlsEnable = mailSmtpStarttlsEnable;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<User> getUsers() {
		return this.users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

}