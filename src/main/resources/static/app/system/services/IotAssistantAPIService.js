
systemModule.service ("IotAssistantAPIService",function(RestAPIService, $q){
	var self = this;	
	
	var iotAssistantBaseUri = "iotassistant/";
	
	self.getIotAssistant = function () {
		var deferred = $q.defer();
		RestAPIService.get(iotAssistantBaseUri).then(function(objectResponse) {
			deferred.resolve(getIotAssistantFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.getCapabilities = function () {
		var deferred = $q.defer();
		RestAPIService.get(iotAssistantBaseUri.concat("capabilities/")).then(function(objectResponse) {
			deferred.resolve(getCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.getDevicesCapabilities = function () {
		var deferred = $q.defer();
		RestAPIService.get(iotAssistantBaseUri.concat("devices-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getDevicesCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function getDevicesCapabilitiesFromResponse(objectResponse) {
		var sensorProperties = getPropertiesFromPropertiesObject(objectResponse.sensorCapabilities.supportedProperties);
		var sensorCapabilities = new TransductorCapabilities(sensorProperties, objectResponse.sensorCapabilities.supportedInterfaces, objectResponse.sensorCapabilities.supportedWatchdogIntervals);
		var actuatorProperties = getPropertiesFromPropertiesObject(objectResponse.actuatorCapabilities.supportedProperties);
		var actuatorCapabilities = new TransductorCapabilities(actuatorProperties, objectResponse.actuatorCapabilities.supportedInterfaces, objectResponse.sensorCapabilities.supportedWatchdogIntervals);
		var cameraCapabilities = new CameraCapabilities(objectResponse.cameraCapabilities.supportedInterfaces, objectResponse.cameraCapabilities.supportedWatchdogIntervals);
		return new DevicesCapabilities(sensorCapabilities, actuatorCapabilities, cameraCapabilities);
		
	}
	
	self.getTransductorsCapabilities = function () {
		var deferred = $q.defer();
		RestAPIService.get(iotAssistantBaseUri.concat("transductors-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getTransductorsCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function getIotAssistantFromResponse(objectResponse) {
		var iotAssistantCapabilities = getCapabilitiesFromResponse(objectResponse.capabilities);
		return new IotAssistant(iotAssistantCapabilities, objectResponse.platformName);
	} 
	
	function getCapabilitiesFromResponse(objectResponse) {
		var transductorsCapabilities = getTransductorsCapabilitiesFromResponse(objectResponse);
		var mqttInterfaceCapabilities = new IotAssistantMqttInterfaceCapabilities(objectResponse.mqttInterfaceCapabilities.interfaceName, objectResponse.mqttInterfaceCapabilities.available, objectResponse.mqttInterfaceCapabilities.broker);
		var ruleCapabilities = new IotAssistantRuleCapabilities(objectResponse.ruleCapabilities.supportedSensorRulesTypes, objectResponse.ruleCapabilities.supportedSensorRulesTimeBetweenTriggers);
		var chartCapabilities = new IotAssistantChartCapabilities(objectResponse.chartCapabilities.supportedChartTypes, objectResponse.chartCapabilities.supportedChartIntervals, objectResponse.chartCapabilities.supportedSampleIntervals);
		var cameraCapabilities = new IotAssistantCameraCapabilities(objectResponse.cameraCapabilities.supportedInterfaces, objectResponse.cameraCapabilities.supportedWatchdogIntervals);
		var notificationsCapabilities = new IotAssistantNotificationsCapabilities(objectResponse.notificationsCapabilities.supportedNotificationsTypes);
		var isTelegramConnected = objectResponse.telegramConnected;
		return new IotAssistantCapabilities(transductorsCapabilities, mqttInterfaceCapabilities, chartCapabilities, cameraCapabilities, notificationsCapabilities, ruleCapabilities, isTelegramConnected);
	} 
	
	function getTransductorsCapabilitiesFromResponse(objectResponse) {
		var sensorProperties = getPropertiesFromPropertiesObject(objectResponse.sensorCapabilities.supportedProperties);
		var actuatorProperties = getPropertiesFromPropertiesObject(objectResponse.actuatorCapabilities.supportedProperties);
		var sensorCapabilities = new SensorCapabilities(sensorProperties, objectResponse.sensorCapabilities.supportedInterfaces, objectResponse.sensorCapabilities.supportedWatchdogIntervals);
		var actuatorCapabilities = new ActuatorCapabilities(actuatorProperties, objectResponse.actuatorCapabilities.supportedInterfaces, objectResponse.sensorCapabilities.supportedWatchdogIntervals);
		return new TransductorsCapabilities(sensorCapabilities, actuatorCapabilities);
		
	}
	
	function getPropertiesFromPropertiesObject(propertiesObject) {
		var properties = [];
		propertiesObject.forEach(propertyObject => {
			var property = new Property(propertyObject.name, propertyObject.unit, propertyObject.digital, propertyObject.minimumValue, propertyObject.maximumValue);
			properties.push(property);
		})
		return properties;
	}
	

	self.powerOff = function () {
		var deferred = $q.defer();
		RestAPIService.post(iotAssistantBaseUri.concat("powerOff/")).then(function() {
			deferred.resolve();
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

});

