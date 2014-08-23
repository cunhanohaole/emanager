package br.com.tscpontual.test;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.tscpontual.contacts.manager.ContactsManagerImpl;
import br.com.tscpontual.contacts.model.AddressGroup;

public class TestListGroups extends SpringTestCase {

	@Autowired
	private ContactsManagerImpl contactsManagerImpl;
	
	@Test
	@Ignore
	public void shouldGetGroupsList() {
		List<AddressGroup> groups = contactsManagerImpl.listGroups("marcelo");
		for(AddressGroup g : groups) {
			System.out.println(g.getTitle());
		}
	}
	
}
