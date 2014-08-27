package br.com.tscpontual.email.dao;

import br.com.tscpontual.user.model.SenderConfig;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class SenderConfigDAOImpl implements SenderConfigDAO {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<SenderConfig> getSenderConfigListForUser(String username) {
        Query query = em.createQuery("select sc from SenderConfig sc where sc.user.username = :username");
        return query.setParameter("username", username).getResultList();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public SenderConfig persistSenderConfig(SenderConfig senderConfig) {
        return em.merge(senderConfig);
    }

    @Override
    public void deleteSenderConfig(SenderConfig senderConfig){
        em.remove(senderConfig);
    }

    @Override
    public SenderConfig getSenderConfig(Integer senderConfigId) {
        return em.find(SenderConfig.class, senderConfigId);
    }


}
