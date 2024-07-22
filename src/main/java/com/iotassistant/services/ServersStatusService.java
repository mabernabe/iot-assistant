package com.iotassistant.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.iotassistant.models.ServerStatus;
import com.iotassistant.models.TelegramBotManager;
import com.iotassistant.models.devices.TransductorInterfaceTypeEnum;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.mqtt.MqttTransductorsController;

@Service
public class ServersStatusService {
	
	private @Autowired
	TelegramBotManager telegramBotManager;
	
	@Value("${mqtt.broker.url}")
	private String mqttBroker;
	
	@Autowired
	MqttTransductorsController mqttTransductorsController;
	
	

	public List<ServerStatus> getServersStatus() {
		List<ServerStatus> serversStatus = new ArrayList<ServerStatus>();
		boolean isMqttConnected = mqttTransductorsController.isConnected();
		String mqttBroker = this.mqttBroker;
		serversStatus.add(new ServerStatus( TransductorInterfaceTypeEnum.MQTT.toString(), isMqttConnected, "Broker: " + mqttBroker));
		serversStatus.add(new ServerStatus(NotificationTypeEnum.TELEGRAM.toString(), telegramBotManager.connected(), "Bot: " + telegramBotManager.getBotUsername()));
		return serversStatus;
		
	}	

}
