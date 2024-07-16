package com.iotassistant.services;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.iotassistant.controllers.MQTTTransductorsController;
import com.iotassistant.models.transductor.TransductorInterface;
import com.iotassistant.models.transductor.TransductorInterfaceVisitor;
import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;
import com.iotassistant.models.transductormqttinterface.SensorMqttInterface;

public class TransductorSetDownInterfaceService  implements TransductorInterfaceVisitor {
	
	private MQTTTransductorsController mqttTransductorsController;

	public TransductorSetDownInterfaceService() {
		super();
		this.mqttTransductorsController = MQTTTransductorsController.getInstance();
		
	}

	public void setDown(TransductorInterface transductorInterface)  {
		transductorInterface.accept(this);	
	}

	@Override
	public void visit(SensorMqttInterface sensorMqttInterface) {
		try {
			mqttTransductorsController.unsubscribe(sensorMqttInterface);
		} catch (MqttException e) {
		}	
	}

	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		try {
			mqttTransductorsController.unsubscribe(actuatorMqttInterface);
		}  catch (MqttException e) {
			e.printStackTrace();
		}	
	}


}
