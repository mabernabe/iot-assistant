package com.iotassistant.controllers;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.iotassistant.models.CameraHttpInterface;
import com.iotassistant.models.devices.CameraInterfaceException;
import com.iotassistant.utils.HttpClient;

@Controller
public class HttpDevicesController {
	
	private HttpClient httpClient;
	
	private static HttpDevicesController instance;
	
	Logger logger = LoggerFactory.getLogger(HttpDevicesController.class);

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
			logger.error(e.getLocalizedMessage());
			throw new CameraInterfaceException(e.getMessage());
		}
	}

}
