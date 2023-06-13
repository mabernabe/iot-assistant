package com.iotassistant.models.transductor;

import java.util.List;

public interface TransductorInterface {

	List<Property> getProperties();
	
	public abstract void setUp() throws TransductorInterfaceException;
	
	public abstract void setDown() throws TransductorInterfaceException;

}
