package br.com.tscpontual.contacts.dao;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.tscpontual.contacts.model.AddressGroup;
import br.com.tscpontual.contacts.model.Contact;
import br.com.tscpontual.exception.RecordNotFoundException;
import br.com.tscpontual.util.SecurityHelper;

@Repository
public class ContactsDAOImpl implements ContactsDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	public List<AddressGroup> listGroups() {
		return em.createQuery("select g from AddressGroup g where g.active = true order by g.title asc", AddressGroup.class).getResultList();
	}

	@Override
	public AddressGroup loadGroup(int groupId) {
		return em.find(AddressGroup.class, groupId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public AddressGroup persistGroup(AddressGroup group) {
		group.setLastEditedTimestamp(new Timestamp(new Date().getTime()));
		group.setUserStamp(SecurityHelper.getUserName());
		return em.merge(group);
	}
	
	@Override
	public AddressGroup loadGroup(Integer groupId) {
		return em.find(AddressGroup.class, groupId);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void inactiveGroup(Integer groupId) throws RecordNotFoundException {
		AddressGroup group = loadGroup(groupId);
		if(group != null) {
			group.setActive(false);
			em.persist(group);
		}
		else {
			throw new RecordNotFoundException("Erro ao inativar grupo!", "Tentativa de inativar grupo nao existente!");
		}
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Contact persistContact(Contact contact) {
		contact.setUserStamp(SecurityHelper.getUserName());
		return em.merge(contact);
	}
	
	@Override
	public Contact loadContact(Integer contactId) {
		return em.find(Contact.class, contactId);
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void deleteContact(Integer contactId) throws RecordNotFoundException {
		Contact contact = loadContact(contactId);
		if(contact != null) {
			em.remove(contact);
		}
		else {
			throw new RecordNotFoundException("Erro ao excluir contato!", "Tentativa de excluir contato nao existente!");
		}
	}

	@Override
	public List<Contact> listEmailAddresses(int groupId, int firstResult, int maxResults) {
		return em
				.createQuery(
						"select c from Contact c where c.addressGroup.id = :groupId",
						Contact.class).setParameter("groupId", groupId)
				.setFirstResult(firstResult).setMaxResults(maxResults)
				.getResultList();
	}
	
	@Override
	public int getContactNumberOfRecords(int groupId){
		Object result = em.createNativeQuery("select count(*) from email_address e where e.group_id = :groupId").setParameter("groupId", groupId).getSingleResult();
		return ((BigInteger) result).intValue();
	}

	@Override
	public AddressGroup loadGroupByTitle(String title) {
		List<AddressGroup> list = em.createQuery("select g from AddressGroup g where g.title = :title", AddressGroup.class).setParameter("title", title).getResultList();
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}

	@Override
	public Contact loadContactByAddress(String address) {
		List<Contact> list = em.createQuery("select c from Contact c where c.address = :address", Contact.class).setParameter("address", address).getResultList();
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	public Contact loadContactByAddressAndGroup(String address, int groupId) {
		List<Contact> list = em
				.createQuery("select c from Contact c where c.address = :address and c.addressGroup.id = :groupId",Contact.class)
				.setParameter("address", address)
				.setParameter("groupId", groupId)
				.getResultList();
		if(list != null && !list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void setContactAsInvalid(String address, String invalidReason) {
		em.createNativeQuery("update email_address set invalid = 1, invalid_reason = :invalidReason where address = :address")
			.setParameter("address", address)
			.setParameter("invalidReason", invalidReason)
			.executeUpdate();
			
	}

}