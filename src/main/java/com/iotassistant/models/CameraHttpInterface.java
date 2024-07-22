package com.iotassistant.models;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.iotassistant.models.devices.CameraInterface;
import com.iotassistant.models.devices.CameraInterfaceException;
import com.iotassistant.models.devices.CameraInterfaceVisitor;

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


	public String getUrl() {
		return url;
	}


	@Override
	public void accept(CameraInterfaceVisitor cameraInterfaceVisitor) throws CameraInterfaceException {
		cameraInterfaceVisitor.visit(this);
		
	}
	
	
}
