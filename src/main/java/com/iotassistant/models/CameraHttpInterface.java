package com.iotassistant.models;

import java.io.IOException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
@DiscriminatorValue("cameraHTTPInterface")
public class CameraHttpInterface extends CameraInterface{

	private String url;

	@Transient
	private HttpPlatformInterface httpPlatformInterface;

	public CameraHttpInterface() {
		super();
		httpPlatformInterface = PlatformInterfacesFactory.getInstance().getHttpPlatformInterface();		
	}


	public CameraHttpInterface(String url) {
		this();
		this.url = url;

	}


	@Override
	public byte[] getPicture() throws CameraInterfaceException {
		try {
			return httpPlatformInterface.getForObject(url + "/jpg", byte[].class);
		} catch(IOException e) {
			throw new CameraInterfaceException(e.getMessage());
		}
	}

}
