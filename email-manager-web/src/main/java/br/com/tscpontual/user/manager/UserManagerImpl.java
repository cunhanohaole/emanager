package br.com.tscpontual.user.manager;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.tscpontual.contacts.dao.ContactsDAO;
import br.com.tscpontual.contacts.model.AddressGroup;
import br.com.tscpontual.user.dao.UserDAO;
import br.com.tscpontual.user.model.Role;
import br.com.tscpontual.user.model.SenderConfig;
import br.com.tscpontual.user.model.SenderConfigFactory;
import br.com.tscpontual.user.model.Signature;
import br.com.tscpontual.user.model.User;

public class UserManagerImpl implements UserManager {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private ContactsDAO contactsDAO;
	
	@Autowired
	private SenderConfigFactory senderConfigFactory;
	
	@Override
	public User newUser(String username, String password, boolean active, String userRole, String emailAccount, String emailPassword, String hostname) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setActive(active);
		Role role = new Role();
		role.setRole(userRole);
		List<Role> roles = new ArrayList<Role>(1);
		roles.add(role);
		user.setRoles(roles);
        user = userDAO.persistUser(user);
        SenderConfig senderConfig = senderConfigFactory.create(user, emailAccount);
        userDAO.persistSenderConfig(senderConfig);
        return user;
	}
	
	@Override
	public User editUser(String username, String password, boolean active, String userRole, String emailAccount, String emailPassword, String hostname) {
		User user = userDAO.loadUser(username);
		user.setUsername(username);
		user.setPassword(password);
		user.setActive(active);
		List<Role> roles = user.getRoles();
		roles.clear();
		Role role = userDAO.loadRole(userRole);
		roles.add(role);
		return userDAO.persistUser(user);
	}
	
	@Override
	public List<User> listUsers() {
		return userDAO.listUsers();
	}

	@Override
	public List<Role> listRoles() {
		return userDAO.listRoles();
	}
	
	@Override
	public User loadUser(String username) {
		return userDAO.loadUser(username);
	}
	
	@Override
	public void saveUserContactAccess(List<String> accessGroups, String username) {
		User user = loadUser(username);
		user.getAddressGroups().clear();
		if(CollectionUtils.isNotEmpty(accessGroups)) {
			for (String groupId : accessGroups) {
				AddressGroup group = contactsDAO.loadGroup(Integer.parseInt(groupId));
				user.getAddressGroups().add(group);
			}
		}
		userDAO.persistUser(user);
	}

	@Override
	public User updateUser(String password, boolean active, String userRole) {
		return null;
	}

	@Override
	public List<Signature> listSignatures(String username) {
		return userDAO.listSignatures(username);
	}

	@Override
	public Signature newSignature(String username, String title, String signature) {
		User user = userDAO.loadUser(username);
		Signature sig = new Signature(title, signature, user);
		return userDAO.persistSignature(sig);
	}
	
	@Override
	public Signature updateSignature(Integer signatureId, String title, String signature) {
		Signature sig = loadSignature(signatureId);
		sig.setTitle(title);
		sig.setHtmlSignature(signature);
		return userDAO.persistSignature(sig);
	}
	
	@Override
	public Signature loadSignature(Integer signatureId) {
		return userDAO.loadSignature(signatureId);
	}

}
