package br.com.tscpontual.user.controller;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.tscpontual.contacts.manager.ContactsManager;
import br.com.tscpontual.contacts.model.AddressGroup;
import br.com.tscpontual.controller.BaseController;
import br.com.tscpontual.user.manager.UserManager;
import br.com.tscpontual.user.model.Role;
import br.com.tscpontual.user.model.Signature;
import br.com.tscpontual.user.model.User;
import br.com.tscpontual.util.JqGridResponse;
import br.com.tscpontual.util.SecurityHelper;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController {

	@Autowired
	private UserManager userManager;
	
	@Autowired
	private ContactsManager contactsManager;
	
	@RequestMapping(value = "new", method = RequestMethod.GET)
	public String loadCreateUser(){
		return "users/NewUser";
	}
	
	@RequestMapping(value = "loadList", method = RequestMethod.GET)
	public String loadListUsers(){
		return "users/ListUsers";
	}
	
	@RequestMapping(value = "signatures", method = RequestMethod.GET)
	public String loadSignatures(){
		return "users/Signatures";
	}
	
	@RequestMapping(value = "loadContactAccess", method = RequestMethod.GET)
	public String loadContactAccess(){
		return "users/ContactAccess";
	}
	
	@RequestMapping(value = "persist", method = RequestMethod.POST)
	public @ResponseBody boolean newUser(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "active", required = true) boolean active,
			@RequestParam(value = "userRole", required = true) String userRole,
			@RequestParam(value = "emailAccount", required = true) String emailAccount,
			@RequestParam(value = "emailPassword", required = false) String emailPassword,
			@RequestParam(value = "hostname", required = false) String hostname) {
		userManager.newUser(username, password, active, userRole, emailAccount, emailPassword, hostname);
		return true;
	}
	
	@RequestMapping(value = "editUser", method = RequestMethod.POST)
	public @ResponseBody boolean editUser(
			@RequestParam(value = "username", required = true) String username,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "active", required = true) boolean active,
			@RequestParam(value = "userRole", required = true) String userRole,
			@RequestParam(value = "emailAccount", required = true) String emailAccount,
			@RequestParam(value = "emailPassword", required = false) String emailPassword,
			@RequestParam(value = "hostname", required = false) String hostname) {
		userManager.editUser(username, password, active, userRole, emailAccount, emailPassword, hostname);
		return true;
	}
	
	@RequestMapping(value = "listRoles", method = RequestMethod.GET)
	public @ResponseBody List<Role> listRoles(){
		return userManager.listRoles();
	}
	
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public @ResponseBody JqGridResponse<User> listUsers(){
		List<User> list = userManager.listUsers();
		JqGridResponse<User> response = new JqGridResponse<User>();
		if(list != null && !list.isEmpty()){
			response.setRows(list);
			response.setRecords(Long.valueOf(list.size()).toString());
		}
		response.setTotal("1");
		response.setPage("1");
		return response;
	}
	
	@RequestMapping(value = "listUserGroups", method = RequestMethod.GET)
	public @ResponseBody JqGridResponse<AddressGroup> getUserGroups(@RequestParam("username") String username) {
		List<AddressGroup> groups = contactsManager.listGroups(username);
		JqGridResponse<AddressGroup> response = new JqGridResponse<AddressGroup>();
		if(groups != null && !groups.isEmpty()){
			response.setRows(groups);
			response.setRecords(Long.valueOf(groups.size()).toString());
		}
		response.setTotal("1");
		response.setPage("1");
		return response;
	}
	
	@RequestMapping(value = "saveContactAccess", method = RequestMethod.POST)
	@ResponseBody
	public boolean saveUserContactAccess(@RequestBody List<String> addressGroups, @RequestParam("username") String username) {
		userManager.saveUserContactAccess(addressGroups, username);
		return true;
	}
	
	@RequestMapping(value = "listSignatures", method = RequestMethod.GET)
	public @ResponseBody JqGridResponse<Signature> listSignatures(){
		String username = SecurityHelper.getUserName();
		JqGridResponse<Signature> response = new JqGridResponse<Signature>();
		List<Signature> signatures = userManager.listSignatures(username);
		if(CollectionUtils.isNotEmpty(signatures)) {
			response.setRows(signatures);
			response.setRecords(Long.valueOf(signatures.size()).toString());
		}
		response.setTotal("1");
		response.setPage("1");
		return response;
	}
	
	@RequestMapping(value = "loadSignature", method = RequestMethod.GET)
	@ResponseBody
	public Signature loadSignature(@RequestParam(value = "idSignature") String idSignature) {
		return userManager.loadSignature(Integer.parseInt(idSignature));
	}
	
	@RequestMapping(value = "saveSignature", method = RequestMethod.POST)
	@ResponseBody
	public boolean saveSignature(@RequestParam(value = "idSignature", required = false) String idSignature,
			@RequestParam("title") String title, 
			@RequestParam("signature") String signature) {
		String username = SecurityHelper.getUserName();
		if(StringUtils.isNotEmpty(idSignature)) {
			userManager.updateSignature(Integer.parseInt(idSignature), title, signature);
		}
		else {
			userManager.newSignature(username, title, signature);
		}
		return true;
	}
	
}
