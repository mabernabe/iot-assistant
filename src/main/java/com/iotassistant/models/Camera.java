package com.iotassistant.models;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.iotassistant.models.transductor.WatchdogInterval;

@Entity
@DiscriminatorValue("Camera")
@Table(name="camera")
public class Camera extends Device {
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	protected CameraInterface cameraInterface;

	public Camera() {
		super();
	}

	public Camera(String name, String description, CameraInterface cameraInterface, WatchdogInterval watchdogInterval) {
		super(name, description, watchdogInterval);
		this.cameraInterface = cameraInterface;
	}

	public void setInterface(CameraInterface cameraInterface) {
		this.cameraInterface = cameraInterface;
		
	}

	@Override
	public void accept(DeviceVisitor deviceVisitor) {
		deviceVisitor.visit(this);
		
	}

	public CameraInterface getInterface() {
		return this.cameraInterface;
		
	}


}
