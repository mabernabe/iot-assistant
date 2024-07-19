package com.iotassistant.services;

import org.eclipse.paho.client.mqttv3.MqttException;

import com.iotassistant.controllers.MqttTransductorsController;
import com.iotassistant.models.transductor.TransductorInterface;
import com.iotassistant.models.transductor.TransductorInterfaceVisitor;
import com.iotassistant.models.transductormqttinterface.ActuatorMqttInterface;
import com.iotassistant.models.transductormqttinterface.MqttInterface;
import com.iotassistant.models.transductormqttinterface.SensorMqttInterface;

class TransductorSetDownInterfaceService  implements TransductorInterfaceVisitor {
	

	public TransductorSetDownInterfaceService() {
		super();	
	}

	public void setDown(TransductorInterface transductorInterface)  {
		transductorInterface.accept(this);	
	}

	@Override
	public void visit(SensorMqttInterface sensorMqttInterface) {
		this.setDownMqttInterface(sensorMqttInterface);
	}
	
	private void setDownMqttInterface(MqttInterface mqttInterface) {		
		try {
			MqttTransductorsController.getInstance().unsubscribe(mqttInterface);
		} catch (MqttException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void visit(ActuatorMqttInterface actuatorMqttInterface) {
		this.setDownMqttInterface(actuatorMqttInterface);
	}


}
