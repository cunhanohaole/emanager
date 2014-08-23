package br.com.tscpontual.contacts.dao;

import java.util.List;

import br.com.tscpontual.contacts.model.AddressGroup;
import br.com.tscpontual.contacts.model.Contact;
import br.com.tscpontual.exception.RecordNotFoundException;

public interface ContactsDAO {

	List<AddressGroup> listGroups();
	
	AddressGroup loadGroup(int groupId);
	
	AddressGroup persistGroup(AddressGroup group);
	
	Contact persistContact(Contact contact);
	
	List<Contact> listEmailAddresses(int groupId, int firstResult, int maxResults);
	
	AddressGroup loadGroupByTitle(String title);
	
	Contact loadContactByAddress(String address);

	Contact loadContactByAddressAndGroup(String address, int groupId);
	
	int getContactNumberOfRecords(int groupId);

	void setContactAsInvalid(String address, String invalidReason);

	Contact loadContact(Integer contactId);

	void deleteContact(Integer contactId) throws RecordNotFoundException;

	AddressGroup loadGroup(Integer groupId);

	void inactiveGroup(Integer groupId) throws RecordNotFoundException;
	
}
