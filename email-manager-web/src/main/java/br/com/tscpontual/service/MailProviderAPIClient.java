package br.com.tscpontual.service;

import java.util.Date;

import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.service.mailjet.model.ReportEmailBounceResponse;

public interface MailProviderAPIClient {

	ReportEmailBounceResponse listEmailBounces(Integer limit, Date from, Date to) throws TechnicalException;
	
}
