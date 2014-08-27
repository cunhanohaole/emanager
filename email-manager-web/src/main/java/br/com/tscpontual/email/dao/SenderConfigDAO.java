package br.com.tscpontual.email.dao;

import br.com.tscpontual.user.model.SenderConfig;

import java.util.List;

public interface SenderConfigDAO {
    List<SenderConfig> getSenderConfigListForUser(String username);

    SenderConfig persistSenderConfig(SenderConfig senderConfig);

    void deleteSenderConfig(SenderConfig senderConfig);

    SenderConfig getSenderConfig(Integer senderConfigId);
}
