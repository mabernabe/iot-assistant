var stationAPIService = angular.module('stationAPIService', ['restAPIService']);

stationAPIService.service ("StationAPIService",function(RestAPIService, $q){
	var self = this;	
	
	var stationBaseUri = "station/";
	
	
	self.getStation = function () {
		var deferred = $q.defer();
		RestAPIService.get(stationBaseUri).then(function(objectResponse) {
			deferred.resolve(getStationFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.getCapabilities = function () {
		var deferred = $q.defer();
		RestAPIService.get(stationBaseUri.concat("capabilities/")).then(function(objectResponse) {
			deferred.resolve(getCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function getStationFromResponse(objectResponse) {
		var stationCapabilities = getCapabilitiesFromResponse(objectResponse.capabilities);
		return new Station(stationCapabilities, objectResponse.platformName);
	} 
	
	function getCapabilitiesFromResponse(objectResponse) {
		var sensorProperties = getPropertiesFromPropertiesObject(objectResponse.sensorCapabilities.supportedProperties);
		var actuatorProperties = getPropertiesFromPropertiesObject(objectResponse.actuatorCapabilities.supportedProperties);
		var sensorCapabilities = new StationTransductorCapabilities(sensorProperties, objectResponse.sensorCapabilities.supportedInterfaces, objectResponse.sensorCapabilities.supportedWatchdogIntervals);
		var actuatorCapabilities = new StationTransductorCapabilities(actuatorProperties, objectResponse.actuatorCapabilities.supportedInterfaces, objectResponse.sensorCapabilities.supportedWatchdogIntervals);
		var pinInterfaceCapabilities = new StationPinInterfaceCapabilities(objectResponse.pinInterfaceCapabilities.interfaceName, objectResponse.pinInterfaceCapabilities.available, objectResponse.pinInterfaceCapabilities.availableDigitalPins, objectResponse.pinInterfaceCapabilities.availableAnalogPins, objectResponse.pinInterfaceCapabilities.platformPinInterfaceName);
		var mqttInterfaceCapabilities = new StationMqttInterfaceCapabilities(objectResponse.mqttInterfaceCapabilities.interfaceName, objectResponse.mqttInterfaceCapabilities.available, objectResponse.mqttInterfaceCapabilities.broker);
		var ruleCapabilities = new StationRuleCapabilities(objectResponse.ruleCapabilities.supportedSensorRulesTypes, objectResponse.ruleCapabilities.supportedSensorRulesTimeBetweenTriggers);
		var chartCapabilities = new StationChartCapabilities(objectResponse.chartCapabilities.supportedChartTypes, objectResponse.chartCapabilities.supportedChartIntervals, objectResponse.chartCapabilities.supportedSampleIntervals);
		var cameraCapabilities = new StationCameraCapabilities(objectResponse.cameraCapabilities.supportedInterfaces, objectResponse.cameraCapabilities.supportedWatchdogIntervals);
		var notificationsCapabilities = new StationNotificationsCapabilities(objectResponse.notificationsCapabilities.supportedNotificationsTypes);
		var isTelegramConnected = objectResponse.telegramConnected;
		return new StationCapabilities(sensorCapabilities, actuatorCapabilities, pinInterfaceCapabilities, mqttInterfaceCapabilities, chartCapabilities, cameraCapabilities, notificationsCapabilities, ruleCapabilities, isTelegramConnected);
	} 
	
	function getPropertiesFromPropertiesObject(propertiesObject) {
		var properties = [];
		propertiesObject.forEach(propertyObject => {
			var property = new Property(propertyObject.name, propertyObject.digital, propertyObject.minimumValue, propertyObject.maximumValue);
			properties.push(property);
		})
		return properties;
	}
	

	self.powerOff = function () {
		var deferred = $q.defer();
		RestAPIService.post(stationBaseUri.concat("powerOff/")).then(function() {
			deferred.resolve();
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

});

