package br.com.tscpontual.email.manager;

import br.com.tscpontual.email.dao.SenderConfigDAO;
import br.com.tscpontual.user.dao.UserDAO;
import br.com.tscpontual.user.model.SenderConfig;
import br.com.tscpontual.user.model.SenderConfigFactory;
import br.com.tscpontual.user.model.User;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

public class SenderConfigManagerImpl implements SenderConfigManager {

    @Resource
    private SenderConfigDAO senderConfigDAO;

    @Resource
    private SenderConfigFactory senderConfigFactory;

    @Resource
    private UserDAO userDAO;

    @Override
    public List<SenderConfig> getSenderConfigListForUser(String username) {
        return senderConfigDAO.getSenderConfigListForUser(username);
    }

    @Override
    public SenderConfig createNewSenderConfig(String username, String emailAddress) {
        User user = userDAO.loadUser(username);
        SenderConfig senderConfig = senderConfigFactory.create(user, emailAddress);
        return senderConfigDAO.persistSenderConfig(senderConfig);
    }

    @Override
    public SenderConfig updateSenderConfig(Integer senderConfigId, String emailFrom) {
        SenderConfig senderConfig = getSenderConfig(senderConfigId);
        senderConfig.setFrom(emailFrom);
        return senderConfigDAO.persistSenderConfig(senderConfig);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteSenderConfig(Integer senderConfigId){
        SenderConfig senderConfig = getSenderConfig(senderConfigId);
        senderConfigDAO.deleteSenderConfig(senderConfig);
    }

    @Override
    public SenderConfig getSenderConfig(Integer senderConfigId) {
        return senderConfigDAO.getSenderConfig(senderConfigId);
    }

}
