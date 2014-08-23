package br.com.tscpontual.user.model;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import br.com.tscpontual.contacts.model.AddressGroup;


/**
 * The persistent class for the user database table.
 * 
 */
@Entity
@Table(name="user")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String username;

	private boolean active;

	private String password;
	
	//bi-directional many-to-one association to SenderConfig
	@ManyToOne
	@JoinColumn(name="sender_config_id")
	private SenderConfig senderConfig;

	//bi-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(
		name="user_has_role"
		, joinColumns={
			@JoinColumn(name="username")
			}
		, inverseJoinColumns={
			@JoinColumn(name="role")
			}
		)
	private List<Role> roles;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(
		name="user_has_address_group"
		, joinColumns={
			@JoinColumn(name="user_username")
			}
		, inverseJoinColumns={
			@JoinColumn(name="address_group_id")
			}
		)
	private Set<AddressGroup> addressGroups;
	
	//bi-directional many-to-one association to Signature
	@JsonIgnore
	@OneToMany(mappedBy="user")
	private List<Signature> signatures;

	public User() {
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return this.roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public SenderConfig getSenderConfig() {
		return senderConfig;
	}

	public void setSenderConfig(SenderConfig senderConfig) {
		this.senderConfig = senderConfig;
	}

	public Set<AddressGroup> getAddressGroups() {
		return addressGroups;
	}

	public void setAddressGroups(Set<AddressGroup> addressGroups) {
		this.addressGroups = addressGroups;
	}

	public List<Signature> getSignatures() {
		return signatures;
	}

	public void setSignatures(List<Signature> signatures) {
		this.signatures = signatures;
	}

}