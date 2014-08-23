package br.com.tscpontual.scheduler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.tscpontual.email.manager.EMailManager;
import br.com.tscpontual.exception.TechnicalException;

@Service
public class CheckInvalidEmailsCron implements CronScheduler {

	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(CheckInvalidEmailsCron.class);
	
	@Autowired
	private EMailManager emailManager;
	
	@Override
	public void run() {
		try {
			log.info("running checkInvalidSentEmails.");
			emailManager.checkInvalidSentEmails();
			log.info("checkInvalidSentEmails succesfully run.");
		} catch (TechnicalException e) {
			log.error("error while running checkInvalidSentEmails.", e);
		}		
	}
	
}
