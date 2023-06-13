package com.iotassistant.models.transductorpininterface;

import java.util.HashMap;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.pininterface.PinInterfaceException;
import com.iotassistant.models.transductor.ActuatorInterface;
import com.iotassistant.models.transductor.ActuatorValue;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

@Entity
@DiscriminatorValue("actuatorPINInterface")
public  class ActuatorPinInterface extends ActuatorInterface {
	
	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	ActuatorPinsConfiguration actuatorPinsConfiguration;
	
	@Transient
	ActuatorPinsManager actuatorPinsManager;
	
	

	public ActuatorPinInterface() {
		super();
	}

	public ActuatorPinInterface(HashMap<PropertyActuatedEnum, PinId> actuatorPinsConfiguration)  {
		super();
		this.actuatorPinsConfiguration = new ActuatorPinsConfiguration(actuatorPinsConfiguration);
	}

	@Override
	public List<Property> getProperties() {
		return actuatorPinsConfiguration.getProperties();
	}

	@Override
	public void setValue(PropertyActuatedEnum propertyActuated, String value) throws TransductorInterfaceException  {
		try {
			actuatorPinsManager.setValue(propertyActuated, value);
		} catch (PinInterfaceException e) {
			// TODO Auto-generated catch block
			throw new TransductorInterfaceException(e.getErrorMessage());
		}
		
	}

	@Override
	public List<ActuatorValue> getValues() throws TransductorInterfaceException  {
		try {
			return actuatorPinsManager.getValues();
		} catch (PinInterfaceException e) {
			throw new TransductorInterfaceException(e.getErrorMessage());
		}
	}

	@Override
	public void setUp() throws TransductorInterfaceException {
		try {
			this.initializePinsManager();
		} catch (PinInterfaceException e) {
			throw new TransductorInterfaceException(e.getErrorMessage());
		}	
	}

	private void initializePinsManager() throws PinInterfaceException {
		this.actuatorPinsManager = new ActuatorPinsManager(this.actuatorPinsConfiguration);		
	}

	@Override
	public void setDown() throws TransductorInterfaceException {
		try {
			this.terminatePinsManagers();
		} catch (PinInterfaceException e) {
			throw new TransductorInterfaceException(e.getErrorMessage());
		}
		
	}

	private void terminatePinsManagers() throws PinInterfaceException {
		this.actuatorPinsManager.setDownPins();
		
	}
	
	
	

}
