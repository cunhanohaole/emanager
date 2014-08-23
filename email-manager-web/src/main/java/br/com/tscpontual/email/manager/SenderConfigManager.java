package br.com.tscpontual.email.manager;

import br.com.tscpontual.user.model.SenderConfig;

import java.util.List;

public interface SenderConfigManager {


    List<SenderConfig> getSenderConfigListForUser(String username);

    SenderConfig createNewSenderConfig(String username, String emailAddress);

    SenderConfig updateSenderConfig(SenderConfig senderConfig);

    void deleteSenderConfig(Integer senderConfigId);

    SenderConfig getSenderConfig(Integer senderConfigId);
}
