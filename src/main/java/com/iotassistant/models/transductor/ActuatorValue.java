package com.iotassistant.models.transductor;

import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.utils.Date;

public class ActuatorValue extends TransductorValue{
	
	

	public ActuatorValue(String value, PropertyActuatedEnum propertyActuated) {
		super();
		this.value = value;
		this.property = propertyActuated;
		this.date = Date.getCurrentDate();
	}
	

	public ActuatorValue(String value, PropertyActuatedEnum propertyActuated, String date) {
		this(value, propertyActuated);
		this.date = date;
	}


	public PropertyActuatedEnum getPropertyActuated() {
		return (PropertyActuatedEnum) this.property;
	}
	


}
