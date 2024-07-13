package com.iotassistant.services;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iotassistant.controllers.MQTTTransductorsController;
import com.iotassistant.models.transductor.TransductorInterface;
import com.iotassistant.models.transductor.TransductorInterfaceVisitor;
import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;
import com.iotassistant.models.transductormqttinterface.SensorMqttInterface;

@Service
public class TransductorSetDownInterfaceService  implements TransductorInterfaceVisitor {
	
	@Autowired
	private MQTTTransductorsController mqttTransductorsController;
	
	
	public void setDown(TransductorInterface transductorInterface)  {
		transductorInterface.accept(this);	
	}

	@Override
	public void visit(SensorMqttInterface sensorMqttInterface) {
		try {
			this.mqttTransductorsController.unsubscribe(sensorMqttInterface);
		} catch (MqttException e) {
		}	
	}

	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		try {
			this.mqttTransductorsController.unsubscribe(actuatorMqttInterface);
		}  catch (MqttException e) {
			e.printStackTrace();
		}	
	}


}
