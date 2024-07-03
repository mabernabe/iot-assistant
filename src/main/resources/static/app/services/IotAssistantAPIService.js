var iotAssistantAPIService = angular.module('iotAssistantAPIService', ['restAPIService']);

iotAssistantAPIService.service ("IotAssistantService",function(RestAPIService, $q){
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
	
	function getIotAssistantFromResponse(objectResponse) {
		var iotAssistantCapabilities = getCapabilitiesFromResponse(objectResponse.capabilities);
		return new IotAssistant(iotAssistantCapabilities, objectResponse.platformName);
	} 
	
	function getCapabilitiesFromResponse(objectResponse) {
		var sensorProperties = getPropertiesFromPropertiesObject(objectResponse.sensorCapabilities.supportedProperties);
		var actuatorProperties = getPropertiesFromPropertiesObject(objectResponse.actuatorCapabilities.supportedProperties);
		var sensorCapabilities = new IotAssistantTransductorCapabilities(sensorProperties, objectResponse.sensorCapabilities.supportedInterfaces, objectResponse.sensorCapabilities.supportedWatchdogIntervals);
		var actuatorCapabilities = new IotAssistantTransductorCapabilities(actuatorProperties, objectResponse.actuatorCapabilities.supportedInterfaces, objectResponse.sensorCapabilities.supportedWatchdogIntervals);
		var pinInterfaceCapabilities = new IotAssistantPinInterfaceCapabilities(objectResponse.pinInterfaceCapabilities.interfaceName, objectResponse.pinInterfaceCapabilities.available, objectResponse.pinInterfaceCapabilities.availableDigitalPins, objectResponse.pinInterfaceCapabilities.availableAnalogPins, objectResponse.pinInterfaceCapabilities.platformPinInterfaceName);
		var mqttInterfaceCapabilities = new IotAssistantMqttInterfaceCapabilities(objectResponse.mqttInterfaceCapabilities.interfaceName, objectResponse.mqttInterfaceCapabilities.available, objectResponse.mqttInterfaceCapabilities.broker);
		var ruleCapabilities = new IotAssistantRuleCapabilities(objectResponse.ruleCapabilities.supportedSensorRulesTypes, objectResponse.ruleCapabilities.supportedSensorRulesTimeBetweenTriggers);
		var chartCapabilities = new IotAssistantChartCapabilities(objectResponse.chartCapabilities.supportedChartTypes, objectResponse.chartCapabilities.supportedChartIntervals, objectResponse.chartCapabilities.supportedSampleIntervals);
		var cameraCapabilities = new IotAssistantCameraCapabilities(objectResponse.cameraCapabilities.supportedInterfaces, objectResponse.cameraCapabilities.supportedWatchdogIntervals);
		var notificationsCapabilities = new IotAssistantNotificationsCapabilities(objectResponse.notificationsCapabilities.supportedNotificationsTypes);
		var isTelegramConnected = objectResponse.telegramConnected;
		return new IotAssistantCapabilities(sensorCapabilities, actuatorCapabilities, pinInterfaceCapabilities, mqttInterfaceCapabilities, chartCapabilities, cameraCapabilities, notificationsCapabilities, ruleCapabilities, isTelegramConnected);
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

