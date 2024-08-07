package com.iotassistant.controllers.dtos.devices;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.iotassistant.models.CameraHttpInterface;
import com.iotassistant.models.devices.Camera;
import com.iotassistant.models.devices.WatchdogInterval;

public class HttpCameraDTO extends NewDeviceRequestDTO{
	
	@JsonProperty(required = true)
    @NotNull
	private String url;

	
	public HttpCameraDTO() {
		super();
	}

	@JsonIgnore
	public Camera getCamera() {
		CameraHttpInterface cameraHttpInterface = new CameraHttpInterface(url);
		Camera camera = new Camera(this.getName(), this.getDescription(), cameraHttpInterface, WatchdogInterval.getInstance(this.getWatchdogInterval()));
		camera.setActive(true);
		return camera;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
