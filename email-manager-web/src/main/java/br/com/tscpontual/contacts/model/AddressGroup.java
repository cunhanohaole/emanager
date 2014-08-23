package br.com.tscpontual.contacts.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonIgnore;

import br.com.tscpontual.user.model.User;


/**
 * The persistent class for the address_group database table.
 * 
 */
@Entity
@Table(name="address_group")
public class AddressGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private boolean active;

	@Column(name="created_timestamp")
	private Timestamp createdTimestamp;

	@Column(name="last_edited_timestamp")
	private Timestamp lastEditedTimestamp;

	private String title;

	@Column(name="user_stamp")
	private String userStamp;

	@OneToMany(mappedBy="addressGroup")
	@JsonIgnore
	private List<Contact> emailAddresses;
	
	//bi-directional many-to-many association to User
	@JsonIgnore
	@ManyToMany(mappedBy="addressGroups")
	private List<User> users;

	public AddressGroup() {
	}
	
	public AddressGroup(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getActive() {
		return this.active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getTitle() {
		if(this.title != null) {
			return this.title.trim();
		}
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUserStamp() {
		return this.userStamp;
	}

	public void setUserStamp(String userStamp) {
		this.userStamp = userStamp;
	}

	public List<Contact> getEmailAddresses() {
		return this.emailAddresses;
	}

	public void setEmailAddresses(List<Contact> emailAddresses) {
		this.emailAddresses = emailAddresses;
	}

	public Timestamp getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Timestamp createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Timestamp getLastEditedTimestamp() {
		return lastEditedTimestamp;
	}

	public void setLastEditedTimestamp(Timestamp lastEditedTimestamp) {
		this.lastEditedTimestamp = lastEditedTimestamp;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AddressGroup other = (AddressGroup) obj;
		if (id != other.id)
			return false;
		return true;
	}

}