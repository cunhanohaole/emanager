package br.com.tscpontual.contacts.manager;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import br.com.tscpontual.contacts.model.AddressGroup;
import br.com.tscpontual.contacts.model.Contact;
import br.com.tscpontual.exception.BusinessException;
import br.com.tscpontual.exception.RecordNotFoundException;
import br.com.tscpontual.exception.TechnicalException;

public interface ContactsManager {
	
	public AddressGroup addGroup(String title, boolean active);
	
	List<Contact> listEmailAddresses(int groupId, int page, int numberOfRowsPerPage);
	
	Contact addContact(String name, String address, int groupId);
	
	void importContactsFromFile(MultipartFile file) throws BusinessException, TechnicalException;

	Contact editContact(int contactId, String name, String address,
			int groupId);
	
	int getNumberOfPagesForContactList(int groupId, int numberOfRowsPerPage);

	List<AddressGroup> listGroups(String username);

	List<AddressGroup> listAllGroups();

	void deleteContact(Integer contactId) throws RecordNotFoundException;

	void inactiveGroup(Integer groupId) throws RecordNotFoundException;
	
}
