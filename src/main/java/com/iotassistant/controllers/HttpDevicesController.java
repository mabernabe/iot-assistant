package com.iotassistant.controllers;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Controller;

import com.iotassistant.models.CameraHttpInterface;
import com.iotassistant.models.CameraInterfaceException;
import com.iotassistant.utils.HttpClient;

@Controller
public class HttpDevicesController {
	
	private HttpClient httpClient;
	
	private static HttpDevicesController instance;

	public HttpDevicesController() {
		super();
		this.httpClient = new HttpClient();
	}
	
	@PostConstruct
	private void registerInstance() {
		instance = this;
	} 
	
	
	public static HttpDevicesController getInstance() {
		return instance;
	}


	public byte[] getCameraPicture(CameraHttpInterface cameraHttpInterface) throws CameraInterfaceException {
		try {
			return this.httpClient.getForObject(cameraHttpInterface.getUrl(), byte[].class);
		} catch(Exception e) {
			throw new CameraInterfaceException(e.getMessage());
		}
	}

}
