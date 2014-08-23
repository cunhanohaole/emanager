package br.com.tscpontual.user.manager;

import java.util.List;

import br.com.tscpontual.user.model.Role;
import br.com.tscpontual.user.model.Signature;
import br.com.tscpontual.user.model.User;

public interface UserManager {

	List<User> listUsers();

	User newUser(String username, String password, boolean active, String userRole, String emailAccount, String emailPassword, String hostname);
	
	User updateUser(String password, boolean active, String userRole);
	
	List<Role> listRoles();

	User loadUser(String username);

	void saveUserContactAccess(List<String> accessGroups, String username);
	
	List<Signature> listSignatures(String username);
	
	Signature newSignature(String username, String title, String signature);
	
	Signature updateSignature(Integer signatureId, String title, String signature);

	Signature loadSignature(Integer signatureId);

	User editUser(String username, String password, boolean active,
			String userRole, String emailAccount, String emailPassword,
			String hostname);
	
}
