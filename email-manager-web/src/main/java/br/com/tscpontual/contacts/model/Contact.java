package br.com.tscpontual.contacts.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the email_address database table.
 * 
 */
@Entity
@Table(name="email_address")
public class Contact implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	private int id;

	private String address;

	private String name;
	
	@Column(name="created_timestamp")
	private Timestamp createdTimestamp;

	@Column(name="user_stamp")
	private String userStamp;
	
	private boolean invalid;
	
	@Column(name="invalid_reason")
	private String invalidReason;

	//TODO relation with AddressGroup should be n:n
	@ManyToOne
	@JoinColumn(name="group_id")
	private AddressGroup addressGroup;

	public Contact() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AddressGroup getAddressGroup() {
		return this.addressGroup;
	}

	public void setAddressGroup(AddressGroup addressGroup) {
		this.addressGroup = addressGroup;
	}

	public Timestamp getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Timestamp createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public String getUserStamp() {
		return userStamp;
	}

	public void setUserStamp(String userStamp) {
		this.userStamp = userStamp;
	}
	
	public boolean isInvalid() {
		return invalid;
	}

	public void setInvalid(boolean invalid) {
		this.invalid = invalid;
	}

	public String getInvalidReason() {
		return invalidReason;
	}

	public void setInvalidReason(String invalidReason) {
		this.invalidReason = invalidReason;
	}

}