package com.iotassistant.models.devices;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;

@Entity
public class ActuatorValues  extends TransductorValues<PropertyActuatedEnum>{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	public ActuatorValues() {
		super();
	}



	public ActuatorValues(Map<PropertyActuatedEnum, String> values, String date) {
		super(values, date);
	}
	

}
