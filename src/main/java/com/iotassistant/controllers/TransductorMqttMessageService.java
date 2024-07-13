package com.iotassistant.controllers;

import java.io.IOException;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.iotassistant.models.TransductorVisitor;
import com.iotassistant.models.transductor.Actuator;
import com.iotassistant.models.transductor.Sensor;
import com.iotassistant.models.transductor.Transductor;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.services.TransductorsService;
import com.iotassistant.utils.JSONParser;

public class TransductorMqttMessageService implements TransductorVisitor{
	
	private Transductor transductor;
	
	private MqttMessage message;
	
	private TransductorsService transductorsService;

	
	public TransductorMqttMessageService(Transductor transductor, MqttMessage message,
			TransductorsService transductorsService) {
		super();
		this.transductor = transductor;
		this.message = message;
		this.transductorsService = transductorsService;
	}

	public void updateTransductor() {
		this.transductor.accept(this);
	}
	
	@Override
	public void visit(Sensor sensor) {
		try {
			MQTTSensorValuesDTO sensorValuesDTO;
			sensorValuesDTO = new JSONParser().parseJsonBodyAs(MQTTSensorValuesDTO.class, message.toString());
			List<PropertyMeasuredEnum> sensorProperties = sensor.getPropertiesMeasured();
			if (!sensorValuesDTO.hasErrors(sensorProperties)) {
				transductorsService.updateSensorValues(transductor.getName(), sensorValuesDTO.getSensorValues());
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void visit(Actuator actuator) {
		try {
			MQTTActuatorValuesDTO actuatorValuesDTO;
			actuatorValuesDTO = new JSONParser().parseJsonBodyAs(MQTTActuatorValuesDTO.class, message.toString());
			List<PropertyActuatedEnum> actuatorProperties = actuator.getPropertiesActuated();
			if (!actuatorValuesDTO.hasErrors(actuatorProperties)) {
				transductorsService.updateActuatorValues(actuator.getName(), actuatorValuesDTO.getSensorValues());
			}	
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
