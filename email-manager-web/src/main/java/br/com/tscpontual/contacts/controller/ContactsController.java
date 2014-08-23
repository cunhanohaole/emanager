package br.com.tscpontual.contacts.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import br.com.tscpontual.contacts.manager.ContactsManager;
import br.com.tscpontual.contacts.model.AddressGroup;
import br.com.tscpontual.contacts.model.Contact;
import br.com.tscpontual.controller.BaseController;
import br.com.tscpontual.exception.BusinessException;
import br.com.tscpontual.exception.RecordNotFoundException;
import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.util.AppMessages;
import br.com.tscpontual.util.JqGridResponse;
import br.com.tscpontual.util.ScreenResponse;
import br.com.tscpontual.util.SecurityHelper;

@Controller
@RequestMapping("/contacts")
public class ContactsController extends BaseController {

	private static Logger log = Logger.getLogger(ContactsController.class);
	
	@Autowired
	private ContactsManager  contactsManager;
	
	@RequestMapping(value = "listGroups", method = RequestMethod.GET)
	public @ResponseBody JqGridResponse<AddressGroup> listGroups(){
		JqGridResponse<AddressGroup> response = new JqGridResponse<AddressGroup>();
		List<AddressGroup> groups = contactsManager.listAllGroups();
		response.setRows(groups);
		response.setRecords(Long.valueOf(groups.size()).toString());
		response.setTotal("1");
		response.setPage("1");
		return response;
	}
	
	@RequestMapping(value = "listGroupsForUser", method = RequestMethod.GET)
	public @ResponseBody JqGridResponse<AddressGroup> listGroupsForUser(){
		JqGridResponse<AddressGroup> response = new JqGridResponse<AddressGroup>();
		String username = SecurityHelper.getUserName();
		List<AddressGroup> groups = contactsManager.listGroups(username);
		response.setRows(groups);
		response.setRecords(Long.valueOf(groups.size()).toString());
		response.setTotal("1");
		response.setPage("1");
		return response;
	}
	
	@RequestMapping(value = "addGroup", method = RequestMethod.GET)
	public String loadAddGroup(){
		return "contact/AddGroup";
	}
	
	@RequestMapping(value = "deleteGroup", method = RequestMethod.POST)
	public @ResponseBody ScreenResponse deleteGroup(@RequestParam(value="groupId") String groupId){
		try {
			contactsManager.inactiveGroup(Integer.parseInt(groupId));
		} catch (RecordNotFoundException e) {
			return new ScreenResponse(true, true, e.getTitle(), e.getMessage());
		}
		return new ScreenResponse(false, false, "Sucesso!", "Grupo excluido com sucesso!");
	}
	
	@RequestMapping(value = "addEmailAddress", method = RequestMethod.GET)
	public String loadAddEmailAddress(){
		return "contact/AddEmailAddress";
	}
	
	@RequestMapping(value = "addGroup", method = RequestMethod.POST)
	public @ResponseBody boolean addGroup(@RequestParam(value="title") String title, @RequestParam(value="active") boolean active){
		contactsManager.addGroup(title, active);
		return true;
	}
	
	@RequestMapping(value = "addEmailAddress", method = RequestMethod.POST)
	public @ResponseBody boolean addEmailAddress(@RequestParam(value="name") String name, @RequestParam(value="address") String address, @RequestParam(value="groupId") String groupId){
		contactsManager.addContact(name, address, Integer.parseInt(groupId));
		return true;
	}
	
	@RequestMapping(value = "deleteEmailAddress", method = RequestMethod.POST)
	public @ResponseBody ScreenResponse deleteEmailAddress(@RequestParam(value="contactId") String contactId){
		try {
			contactsManager.deleteContact(Integer.parseInt(contactId));
		} catch (RecordNotFoundException e) {
			return new ScreenResponse(true, true, e.getTitle(), e.getMessage());
		}
		return new ScreenResponse(false, false, "Sucesso!", "Contato excluido com sucesso!");
	}
	
	@RequestMapping(value = "edit", method = RequestMethod.POST)
	public @ResponseBody boolean editContact(@RequestParam(value="contactId") String contactId, @RequestParam(value="name") String name, @RequestParam(value="address") String address, @RequestParam(value="groupId") String groupId){
		contactsManager.editContact(Integer.parseInt(contactId), name, address, Integer.parseInt(groupId));
		return true;
	}
	
	@RequestMapping(value = "loadListEmailAddresses", method = RequestMethod.GET)
	public String loadListEmailAddresses(){
		return "contact/ListEmailAddress";
	}
	
	@RequestMapping(value = "listEmailAddresses", method = RequestMethod.GET)
	public @ResponseBody
	JqGridResponse<Contact> listEmailAddresses(
			@RequestParam(value = "groupId") String groupId,
			@RequestParam(value = "rowsPerPage") String rowsPerPage,
			@RequestParam(value = "page") String page) {
		List<Contact> list = contactsManager.listEmailAddresses(Integer.parseInt(groupId), Integer.parseInt(page), Integer.parseInt(rowsPerPage));
		JqGridResponse<Contact> response = new JqGridResponse<Contact>();
		if(list != null && !list.isEmpty()){
			response.setRows(list);
			int numberOfPages = contactsManager.getNumberOfPagesForContactList(Integer.parseInt(groupId), Integer.parseInt(rowsPerPage));
			response.setRecords(rowsPerPage);
			response.setTotal(String.valueOf(numberOfPages));
			response.setPage(page);
		}
		return response;
	}
	
	@RequestMapping(value = "importContacts", method = RequestMethod.GET)
	public String loadImportContacts(){
		return "contact/ImportContacts";
	}
	
	@RequestMapping(value = "importContacts", method = RequestMethod.POST)
	public @ResponseBody ScreenResponse importContacts(@RequestParam(value = "importContactsFile", required = true) MultipartFile file){
		try {
			contactsManager.importContactsFromFile(file);
			return new ScreenResponse(false, false, AppMessages.SUCCESS_TITLE, "Contatos importados com sucesso!");
		} catch (TechnicalException e) {
			log.error(e);
			return new ScreenResponse(true, false, AppMessages.GENERIC_ERROR_TITLE, "Erro ao importar o arquivo de contatos!");
		}
		catch (BusinessException e) {
			log.error(e);
			return new ScreenResponse(true, false, e.getTitle(), e.getMessage());
		}
	}
	
}