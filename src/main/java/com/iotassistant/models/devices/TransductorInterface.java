package com.iotassistant.models.devices;

public abstract class TransductorInterface {
	
	public abstract void accept(TransductorInterfaceVisitor sensorInterfaceVisitor) ;

}
