package br.com.tscpontual.email.controller;

import br.com.tscpontual.controller.BaseController;
import br.com.tscpontual.email.manager.SenderConfigManager;
import br.com.tscpontual.user.model.SenderConfig;
import br.com.tscpontual.util.JqGridResponse;
import br.com.tscpontual.util.ScreenResponse;
import br.com.tscpontual.util.SecurityHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

@Controller
@RequestMapping("/senderConfig")
public class SenderConfigController extends BaseController {

    @Resource
    private SenderConfigManager senderConfigManager;

    @RequestMapping(value = "listSendersForUser", method = GET)
    @ResponseBody
    public JqGridResponse<SenderConfig> listSendersForUser() {
        JqGridResponse<SenderConfig> response = new JqGridResponse<SenderConfig>();
        String userName = SecurityHelper.getUserName();
        List<SenderConfig> list = senderConfigManager.getSenderConfigListForUser(userName);
        response.setRows(list);
        response.setRecords(Long.valueOf(list.size()).toString());
        response.setTotal("1");
        response.setPage("1");
        return response;
    }

    @RequestMapping(value = "listSendersForUser", method = POST)
    @ResponseBody
    public ScreenResponse createNewSenderConfig(@RequestParam(value = "emailAddress") String emailAddress) {
        String userName = SecurityHelper.getUserName();
        senderConfigManager.createNewSenderConfig(userName, emailAddress);
        return new ScreenResponse("Secesso!", "Novo enviador criado com sucesso!");
    }

    @RequestMapping(value = "deleteSenderConfig", method = POST)
    @ResponseBody
    public ScreenResponse deleteSenderConfig(@RequestParam(value = "senderConfigId") Integer senderConfigId) {
        senderConfigManager.deleteSenderConfig(senderConfigId);
        return new ScreenResponse("Secesso!", "Enviador deleteado com sucesso!");
    }

}
