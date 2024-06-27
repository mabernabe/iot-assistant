package com.iotassistant.models.transductor;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.iotassistant.models.TransductorValues;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;


@Entity
public class SensorValues extends TransductorValues<PropertyMeasuredEnum>{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	

	public SensorValues() {
		super();
	}


	public SensorValues(Map<PropertyMeasuredEnum, String> values, String date) {
		super(values, date);
	}
	

}

