package com.iotassistant.models.devices;

public abstract class DeviceInterface {
	
	public abstract void accept(DeviceInterfaceVisitor deviceInterfaceVisitor) ;

}
