package com.iotassistant.models;

import java.util.ArrayList;
import java.util.List;

public enum CameraInterfaceTypeEnum {
	HTTP("HTTP");
	
	private String string;

	CameraInterfaceTypeEnum(String string) {
		this.string = string;
	}

	@Override
	public String toString() {
		return string;
	}

	public static List<String> getAllInstancesString() {
		List<String> availableInterfaces = new ArrayList<String>();
		for (CameraInterfaceTypeEnum cameraInterface: CameraInterfaceTypeEnum.values()) {
			availableInterfaces.add(cameraInterface.toString());
			
		}
		return availableInterfaces;
	}
	
	

}
