package com.iotassistant.mqtt;

import java.io.IOException;
import java.util.List;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.iotassistant.models.devices.Camera;
import com.iotassistant.models.devices.Device;
import com.iotassistant.models.devices.DeviceVisitor;
import com.iotassistant.models.devices.Gps;
import com.iotassistant.models.devices.transductors.Actuator;
import com.iotassistant.models.devices.transductors.Sensor;
import com.iotassistant.models.devices.transductors.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredEnum;
import com.iotassistant.services.DevicesFacadeService;
import com.iotassistant.utils.JSONParser;

class MqttArrivedMessageService implements DeviceVisitor{
	
	Logger logger = LoggerFactory.getLogger(MqttArrivedMessageService.class);
	
	private Device device;
	
	private MqttMessage message;
	
	private DevicesFacadeService devicesService;

	
	MqttArrivedMessageService(Device device, MqttMessage message,
			DevicesFacadeService devicesService) {
		super();
		this.device = device;
		this.message = message;
		this.devicesService = devicesService;
	}

	void updateDevice() {
		this.device.accept(this);
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
				devicesService.updateSensorValues(device.getName(), sensorValuesDTO.getSensorValues());
			}	
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
	}

	private void logError(Device device, List<String> errors) {
		logger.error(device.getName() + ": " + errors.toString());
	}

	@Override
	public void visit(Actuator actuator) {
		try {
			MqttActuatorValuesDTO actuatorValuesDTO = new JSONParser().parseJsonBodyAs(MqttActuatorValuesDTO.class, message.toString());
			List<PropertyActuatedEnum> actuatorProperties = actuator.getPropertiesActuated();
			if (actuatorValuesDTO.hasErrors(actuatorProperties)) {
				this.logError(actuator, actuatorValuesDTO.getErrors());			} 
			else {
				devicesService.updateActuatorValues(actuator.getName(), actuatorValuesDTO.getSensorValues());
			}	
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
		
	}

	@Override
	public void visit(Gps gps) {
		try {
			MqttGpsPositionDTO gpsPositionDTO = new JSONParser().parseJsonBodyAs(MqttGpsPositionDTO.class, message.toString());
			if (gpsPositionDTO.hasErrors()) {
				this.logError(gps, gpsPositionDTO.getErrors());			
			} 
			else {
				devicesService.updateGpsPosition(gps.getName(), gpsPositionDTO.getPosition());
			}	
		}
		catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
	}
	
	@Override
	public void visit(Camera camera) {
		// Mqtt for Camera is not supported (makes it sense?)
	}

}
