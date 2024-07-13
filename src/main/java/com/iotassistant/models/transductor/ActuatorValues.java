package com.iotassistant.models.transductor;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.iotassistant.models.TransductorValues;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

@Entity
public class ActuatorValues  extends TransductorValues<PropertyActuatedEnum>{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	public ActuatorValues(Map<PropertyActuatedEnum, String> values, String date) {
		super(values, date);
	}
	

}
