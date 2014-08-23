package br.com.tscpontual.contacts.manager;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.TransformingComparator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import edu.emory.mathcs.backport.java.util.Collections;
import br.com.tscpontual.contacts.dao.ContactsDAO;
import br.com.tscpontual.contacts.model.AddressGroup;
import br.com.tscpontual.contacts.model.Contact;
import br.com.tscpontual.contacts.model.ImportedFileReader;
import br.com.tscpontual.exception.BusinessException;
import br.com.tscpontual.exception.RecordNotFoundException;
import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.user.dao.UserDAO;
import br.com.tscpontual.user.model.User;
import br.com.tscpontual.util.AppUtils;
import br.com.tscpontual.util.LowercaseTransformer;
import br.com.tscpontual.util.SecurityHelper;

public class ContactsManagerImpl implements ContactsManager {

	private static Logger log = Logger.getLogger(ContactsManagerImpl.class);
	
	@Autowired
	private ContactsDAO contactsDAO;
	
	@Autowired
	private UserDAO userDAO;
	
	@Override
	public List<AddressGroup> listAllGroups() {
		return listGroups(null);
	}
	
	@Override
	public List<AddressGroup> listGroups(String username) {
		if(StringUtils.isNotBlank(username)) {
			User user = userDAO.loadUser(username);
			Set<AddressGroup> groups = user.getAddressGroups();
			List<AddressGroup> out = new ArrayList<AddressGroup>(groups.size());
			for(AddressGroup g : groups) {
				if(g.getActive()) {
					out.add(g);
				}
			}
			Comparator lowerCaseComparator = new TransformingComparator(new LowercaseTransformer());
			BeanComparator groupsComparator = new BeanComparator("title", lowerCaseComparator);
			Collections.sort(out, groupsComparator);
			return out;
		}
		else {
			return contactsDAO.listGroups();
		}
	}

	@Override
	public AddressGroup addGroup(String title, boolean active) {
		AddressGroup addressGroup = new AddressGroup();
		addressGroup.setTitle(title);
		addressGroup.setActive(active);
		return contactsDAO.persistGroup(addressGroup);
	}
	
	@Override
	public void inactiveGroup(Integer groupId) throws RecordNotFoundException {
		contactsDAO.inactiveGroup(groupId);
	}
	
	@Override
	public Contact addContact(String name, String address, int groupId) {
		Contact emailAddress = new Contact();
		emailAddress.setName(name);
		emailAddress.setAddress(address);
		AddressGroup addressGroup = new AddressGroup(groupId);
		emailAddress.setAddressGroup(addressGroup);
		emailAddress.setCreatedTimestamp(new Timestamp(new Date().getTime()));
		emailAddress.setUserStamp(SecurityHelper.getUserName());
		return contactsDAO.persistContact(emailAddress);
	}
	
	@Override
	public void deleteContact(Integer contactId) throws RecordNotFoundException {
		contactsDAO.deleteContact(contactId);
	}
	
	@Override
	public Contact editContact(int contactId, String name, String address, int groupId) {
		Contact emailAddress = new Contact();
		emailAddress.setId(contactId);
		emailAddress.setName(name);
		emailAddress.setAddress(address);
		AddressGroup addressGroup = new AddressGroup(groupId);
		emailAddress.setAddressGroup(addressGroup);
		emailAddress.setUserStamp(SecurityHelper.getUserName());
		return contactsDAO.persistContact(emailAddress);
	}

	@Override
	public List<Contact> listEmailAddresses(int groupId, int page, int numberOfRowsPerPage) {
		int fistResult = numberOfRowsPerPage * (page - 1);
		return contactsDAO.listEmailAddresses(groupId, fistResult, numberOfRowsPerPage);
	}
	
	@Override
	public int getNumberOfPagesForContactList(int groupId, int numberOfRowsPerPage){
		int rowCount = contactsDAO.getContactNumberOfRecords(groupId);
		int numOfPages = 0;
		int restPart = rowCount % numberOfRowsPerPage;
		int intPart = rowCount / numberOfRowsPerPage;
		if(restPart > 0) numOfPages = intPart + 1;
		else numOfPages = intPart;
		return numOfPages;
	}

	@Override
	public void importContactsFromFile(MultipartFile file) throws BusinessException, TechnicalException {
		ImportedFileReader elr = new ImportedFileReader(file);
		Map<String, HashSet<String>> groups = elr.getEmailListAsGroupsMap();
		Set<String> groupsSet = groups.keySet();
		for (String groupName : groupsSet) {
			HashSet<String> emailsSet = groups.get(groupName);
			AddressGroup addressGroup = contactsDAO.loadGroupByTitle(groupName);
			if(addressGroup == null){
				addressGroup = new AddressGroup();
				addressGroup.setCreatedTimestamp(AppUtils.getCurrentTimestamp());
				addressGroup.setTitle(groupName);
				addressGroup.setActive(true);
				addressGroup = contactsDAO.persistGroup(addressGroup);
			}
			for (String email : emailsSet) {
				Contact contact = contactsDAO.loadContactByAddressAndGroup(email, addressGroup.getId());
				if(contact == null) {
					contact = new Contact();
					contact.setAddress(email);
					contact.setAddressGroup(addressGroup);
					contact.setCreatedTimestamp(AppUtils.getCurrentTimestamp());
					contact.setName("Importado de arquivo");
					contactsDAO.persistContact(contact);
				}
				else
					log.info("Imported email already address exists for this group! (Email address: "
							+ contact.getAddress()
							+ ", Group: "
							+ addressGroup.getId()
							+ " - "
							+ addressGroup.getTitle() + ")");
			}
		}
	}

}
