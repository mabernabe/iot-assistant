package com.iotassistant.models;

import java.io.IOException;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

public class HttpPlatformInterface {
	
	private RestTemplate restTemplate;

	public HttpPlatformInterface() {
		super();
		SimpleClientHttpRequestFactory restTemplateactory = new SimpleClientHttpRequestFactory();
		restTemplateactory.setConnectTimeout(6000);
		restTemplateactory.setReadTimeout(6000);
		this.restTemplate = new RestTemplate(restTemplateactory);
	}
	
	
	public <T> T getForObject(String url,  Class<T> responseType) throws IOException {
		try {
			return this.restTemplate.getForObject(url, responseType);	
		} catch(RestClientException e) {
			throw new IOException(e.getMessage());
		}
	}
	

	

}
