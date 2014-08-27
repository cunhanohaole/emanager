package br.com.tscpontual.user.dao;

import java.util.List;

import br.com.tscpontual.user.model.Role;
import br.com.tscpontual.user.model.SenderConfig;
import br.com.tscpontual.user.model.Signature;
import br.com.tscpontual.user.model.User;

public interface UserDAO {

	User persistUser(User user);
	
	SenderConfig persistSenderConfig(SenderConfig senderConfig);
	
	List<User> listUsers();
	
	List<Role> listRoles();
	
	User loadUser(String username);
	
	Signature persistSignature(Signature signature);
	
	List<Signature> listSignatures(String username);

	Signature loadSignature(Integer signatureId);

	Role loadRole(String role);

    SenderConfig loadSenderConfig(Integer senderConfigId);
}
