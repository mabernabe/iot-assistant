package com.iotassistant.mqtt;

import java.io.IOException;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iotassistant.models.devices.Actuator;
import com.iotassistant.models.devices.Sensor;
import com.iotassistant.models.devices.Transductor;
import com.iotassistant.models.devices.TransductorVisitor;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.services.TransductorsService;
import com.iotassistant.utils.JSONParser;

class MqttArrivedMessageService implements TransductorVisitor{
	
	Logger logger = LoggerFactory.getLogger(MqttArrivedMessageService.class);
	
	private Transductor transductor;
	
	private MqttMessage message;
	
	private TransductorsService transductorsService;

	
	MqttArrivedMessageService(Transductor transductor, MqttMessage message,
			TransductorsService transductorsService) {
		super();
		this.transductor = transductor;
		this.message = message;
		this.transductorsService = transductorsService;
	}

	void updateTransductor() {
		this.transductor.accept(this);
	}
	
	@Override
	public void visit(Sensor sensor) {
		try {
			MqttSensorValuesDTO sensorValuesDTO = new JSONParser().parseJsonBodyAs(MqttSensorValuesDTO.class, message.toString());
			List<PropertyMeasuredEnum> sensorProperties = sensor.getPropertiesMeasured();
			if (sensorValuesDTO.hasErrors(sensorProperties)) {
				this.logError(sensor, sensorValuesDTO.getErrors());
			}
			else {
				transductorsService.updateSensorValues(transductor.getName(), sensorValuesDTO.getSensorValues());
			}	
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
	}

	private void logError(Transductor transductor, List<String> errors) {
		logger.error(transductor.getName() + ": " + errors.toString());
	}

	@Override
	public void visit(Actuator actuator) {
		try {
			MqttActuatorValuesDTO actuatorValuesDTO = new JSONParser().parseJsonBodyAs(MqttActuatorValuesDTO.class, message.toString());
			List<PropertyActuatedEnum> actuatorProperties = actuator.getPropertiesActuated();
			if (actuatorValuesDTO.hasErrors(actuatorProperties)) {
				this.logError(actuator, actuatorValuesDTO.getErrors());			} else {
				transductorsService.updateActuatorValues(actuator.getName(), actuatorValuesDTO.getSensorValues());
			}	
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
		
	}

}
