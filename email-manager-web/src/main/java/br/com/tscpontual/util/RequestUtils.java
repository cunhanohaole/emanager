package br.com.tscpontual.util;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.CommonsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import br.com.tscpontual.exception.HttpStatusException;


public class RequestUtils {
	
	public static void checkStatusCode(ResponseEntity<String> response) {
		HttpStatus statusCode = response.getStatusCode();
		if(statusCode.compareTo(HttpStatus.OK) != 0) {
			throw new HttpStatusException(String.valueOf(statusCode.value()));
		}
	}
	
	public static HttpHeaders setupTokenHeader(String authorization, String publicKey) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + authorization);
		headers.add("X-ConsumerKey", publicKey);
		return headers;
	}
	
	public static HttpHeaders setupHeaders(String authorization) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "OAuth " + authorization);
		return headers;
	}
	
	public static void setupProxy(RestTemplate restTemplate) {
		String proxyServer = getHttpProxyServer();
		String proxyPort = getHttpProxyPort();
		if(StringUtils.isNotEmpty(proxyServer) && StringUtils.isNotEmpty(proxyPort)) {
			HttpClient httpClient = new HttpClient();
			httpClient.getHostConfiguration().setProxy(proxyServer, Integer.parseInt(proxyPort));
			CommonsClientHttpRequestFactory factory = new CommonsClientHttpRequestFactory();
			factory.setHttpClient(httpClient);
			restTemplate.setRequestFactory(factory);
		}
	}
	
	public static String getHttpProxyServer(){
		//return "cbnel216";
		return null;
	}
	
	public static String getHttpProxyPort(){
		//return "3128";
		return null;
	}
	
}
