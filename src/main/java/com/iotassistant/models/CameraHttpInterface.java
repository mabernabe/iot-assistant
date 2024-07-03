package com.iotassistant.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("cameraHTTPInterface")
public class CameraHttpInterface extends CameraInterface{

	private String url;

	public CameraHttpInterface() {
		super();	
	}


	public CameraHttpInterface(String url) {
		this();
		this.url = url;

	}


	@Override
	public byte[] getPicture() throws CameraInterfaceException {
		try {
			return url.getBytes() ; //httpPlatformInterface.getForObject(url + "/jpg", byte[].class);
		} catch(Exception e) {
			throw new CameraInterfaceException(e.getMessage());
		}
	}

}
