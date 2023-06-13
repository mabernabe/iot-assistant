package com.iotassistant.models.transductor;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.utils.Date;

public class SensorMeasure extends TransductorValue{
	
	
	public SensorMeasure(String value, PropertyMeasuredEnum propertyMeasured) {
		super();
		this.value = value;
		this.property = propertyMeasured;
		this.date = Date.getCurrentDate();
	}
	

	public SensorMeasure(String value, PropertyMeasuredEnum propertyMeasured, String date) {
		this(value, propertyMeasured);
		this.date = date;
	}


	public PropertyMeasuredEnum getPropertyMeasured() {
		return (PropertyMeasuredEnum) this.property;
	}


}
