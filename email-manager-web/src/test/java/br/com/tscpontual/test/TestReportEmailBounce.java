package br.com.tscpontual.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.bind.JAXBException;

import org.junit.Assert;
import org.junit.Test;

import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.service.MailProviderAPIClient;
import br.com.tscpontual.service.mailjet.model.EmailBounceItem;
import br.com.tscpontual.service.mailjet.model.EmailBounces;
import br.com.tscpontual.service.mailjet.model.ReportEmailBounceResponse;
import br.com.tscpontual.util.JAXBParser;

public class TestReportEmailBounce extends SpringTestCase {

	@Resource
	private MailProviderAPIClient mailJetAPIClient;
	
	@Resource
	private JAXBParser<ReportEmailBounceResponse> bouncesParser;
	
	@Test
	public void shoudlListEmailBounces() throws TechnicalException {
		ReportEmailBounceResponse response = mailJetAPIClient.listEmailBounces(null, null, null);
		if(response != null) {
			EmailBounces emailBounces = response.getEmailBounces();
			if(emailBounces != null) {
				List<EmailBounceItem> items = emailBounces.getItems();
				Assert.assertNotNull(items);
				Assert.assertNotSame(items.size(), 0);
			}
		}
	}
	
	@Test
	public void shoudlListEmailBouncesLimited() throws TechnicalException {
		ReportEmailBounceResponse response = mailJetAPIClient.listEmailBounces(10, null, null);
		if(response != null) {
			EmailBounces emailBounces = response.getEmailBounces();
			if(emailBounces != null) {
				List<EmailBounceItem> items = emailBounces.getItems();
				Assert.assertNotNull(items);
				Assert.assertEquals(items.size(), 10);
			}
		}
	}
	
	@Test
	public void shoudlListEmailBouncesDateRange() throws TechnicalException, ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date from = sdf.parse("05/03/2013");
		Date to = sdf.parse("07/03/2013");
		ReportEmailBounceResponse response = mailJetAPIClient.listEmailBounces(10, from, to);
		if(response != null) {
			EmailBounces emailBounces = response.getEmailBounces();
			if(emailBounces != null) {
				List<EmailBounceItem> items = emailBounces.getItems();
				Assert.assertNotNull(items);
				Assert.assertEquals(items.size(), 10);
			}
		}
	}
	
	@Test
	public void shouldUnmarshall() throws TechnicalException, JAXBException {
		ReportEmailBounceResponse response = bouncesParser.xmlToObject("<?xml version=\"1.0\" encoding=\"utf-8\" ?><xml><bounces><item><email>jose@comercialautotec.com.br</email><mj_campaign_id>1789750890</mj_campaign_id><mj_contact_id>1052049189</mj_contact_id><blocked>1</blocked><hard_bounce>1</hard_bounce><date_ts>1394453862</date_ts><error_related_to>domain</error_related_to><error>invalid domain</error></item></bounces></xml>", ReportEmailBounceResponse.class);
		if(response != null) {
			EmailBounces emailBounces = response.getEmailBounces();
			if(emailBounces != null) {
				List<EmailBounceItem> items = emailBounces.getItems();
				Assert.assertNotNull(items);
				Assert.assertEquals(items.size(), 1);
				Assert.assertEquals(items.get(0).getEmail(), "jose@comercialautotec.com.br");
				
			}
		}
	}
	
}
