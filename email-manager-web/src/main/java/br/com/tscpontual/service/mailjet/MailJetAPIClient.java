package br.com.tscpontual.service.mailjet;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import br.com.tscpontual.exception.TechnicalException;
import br.com.tscpontual.service.MailProviderAPIClient;
import br.com.tscpontual.service.mailjet.model.ReportEmailBounceResponse;
import br.com.tscpontual.util.AppUtils;
import br.com.tscpontual.util.JAXBParser;
import br.com.tscpontual.util.RequestUtils;

public class MailJetAPIClient implements MailProviderAPIClient {

	private RestTemplate restTemplate;
	
	private JAXBParser<ReportEmailBounceResponse> bouncesParser;
	
	@PostConstruct
	public void init() {
		RequestUtils.setupProxy(restTemplate);
	}
	
	public ReportEmailBounceResponse listEmailBounces(Integer limit, Date from, Date to) throws TechnicalException {
		HttpHeaders headers = RequestUtils.setupHeaders(getAuthorization());
		HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
		String url = getURL(limit, from, to);
		try {
			ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
			RequestUtils.checkStatusCode(response);
			String xmlResponse = response.getBody();
			return bouncesParser.xmlToObject(xmlResponse, ReportEmailBounceResponse.class);
		} catch (JAXBException e) {
			throw new TechnicalException("error while parsing reportEmailbounce response", e);
		} catch (RestClientException e) {
			throw new TechnicalException("error while connecting to reportEmailbounce web service", e);
		}
	}
	
	private String getAuthorization() {
		return "NDk1ODM5MDAwMzViMjg1Njc3MjYyMDE2MTAwMDhkYTI6YWYwZTQzNWI2YTdjZTIxNWY1ZjQ2YjgxMTA3YjdlNDQ=";
	}
	
	private String getURL(Integer limit, Date dateFrom, Date dateTo) {
		StringBuilder baseURL = new StringBuilder("http://api.mailjet.com/0.1/reportEmailbounce");
		if(limit != null || dateFrom != null || dateTo != null) {
			baseURL.append("?");
			if(limit != null) {
				baseURL.append("limit=" + limit.toString());
			}
			if(dateFrom != null) {
				Long epochTimeStamp = AppUtils.dateToEpochTimeStamp(dateFrom);
				baseURL.append("ts_from=" + epochTimeStamp.toString());
			}
			if(dateTo != null) {
				Long epochTimeStamp = AppUtils.dateToEpochTimeStamp(dateTo);
				baseURL.append("ts_from=" + epochTimeStamp.toString());
			}
		}
		return baseURL.toString();
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public JAXBParser<ReportEmailBounceResponse> getBouncesParser() {
		return bouncesParser;
	}

	public void setBouncesParser(JAXBParser<ReportEmailBounceResponse> bouncesParser) {
		this.bouncesParser = bouncesParser;
	}

	
}
