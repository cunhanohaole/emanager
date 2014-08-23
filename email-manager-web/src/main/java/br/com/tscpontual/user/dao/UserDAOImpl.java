package br.com.tscpontual.user.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.tscpontual.user.model.Role;
import br.com.tscpontual.user.model.SenderConfig;
import br.com.tscpontual.user.model.Signature;
import br.com.tscpontual.user.model.User;

@Repository
public class UserDAOImpl implements UserDAO {

	@PersistenceContext
	private EntityManager em;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public User persistUser(User user) {
		return em.merge(user);
	}

	@Override
	public List<User> listUsers() {
		return em.createQuery("select u from User u", User.class).getResultList();
	}

	@Override
	public List<Role> listRoles() {
		return em.createQuery("select r from Role r", Role.class).getResultList();
	}


	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public SenderConfig persistSenderConfig(SenderConfig senderConfig) {
		return em.merge(senderConfig);
	}
	
	@Override
	public User loadUser(String username){
		return em.find(User.class, username);
	}	
	
	@Override
	public Role loadRole(String role) {
		return em.find(Role.class, role);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public Signature persistSignature(Signature signature) {
		return em.merge(signature);
	}

	@Override
	public List<Signature> listSignatures(String username) {
		return em
				.createQuery(
						"select s from Signature s where s.user.username = :username",
						Signature.class).setParameter("username", username)
				.getResultList();
	}
	
	@Override
	public Signature loadSignature(Integer signatureId) {
		return em.find(Signature.class, signatureId);
	}

    @Override
    public SenderConfig loadSenderConfig(Integer senderConfigId) {
        return em.find(SenderConfig.class, senderConfigId);
    }

}
