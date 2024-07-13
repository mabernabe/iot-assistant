package com.iotassistant.models.transductor;

public abstract class TransductorInterface {
	
	public abstract void accept(TransductorInterfaceVisitor sensorInterfaceVisitor) ;

}
