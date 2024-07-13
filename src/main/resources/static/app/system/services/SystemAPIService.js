
systemModule.service ("SystemAPIService",function(RestAPIService, $q){
	var self = this;	
	
	var systemBaseUri = "system/";
	
	self.getSystem = function () {
		var deferred = $q.defer();
		RestAPIService.get(systemBaseUri).then(function(objectResponse) {
			deferred.resolve(getSystemFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.getCapabilities = function () {
		var deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("capabilities/")).then(function(objectResponse) {
			deferred.resolve(getCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.getDevicesCapabilities = function () {
		var deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("devices-capabilities/")).then(function(objectResponse) {
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
	
	self.getRulesCapabilities = function () {
		var deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("rules-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getRulesCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function getRulesCapabilitiesFromResponse(objectResponse) {
		return new RuleCapabilities(objectResponse.supportedSensorRulesTypes, objectResponse.supportedSensorRulesTimeBetweenTriggers);
	} 
	
	self.getTransductorsCapabilities = function () {
		var deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("transductors-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getTransductorsCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function getSystemFromResponse(objectResponse) {
		var systemCapabilities = getCapabilitiesFromResponse(objectResponse.capabilities);
		return new System(systemCapabilities, objectResponse.platformName);
	} 
	
	function getCapabilitiesFromResponse(objectResponse) {
		var transductorsCapabilities = getTransductorsCapabilitiesFromResponse(objectResponse);
		var mqttInterfaceCapabilities = new MqttInterfaceCapabilities(objectResponse.mqttInterfaceCapabilities.interfaceName, objectResponse.mqttInterfaceCapabilities.available, objectResponse.mqttInterfaceCapabilities.broker);
		var ruleCapabilities = new RuleCapabilities(objectResponse.ruleCapabilities.supportedSensorRulesTypes, objectResponse.ruleCapabilities.supportedSensorRulesTimeBetweenTriggers);
		var chartCapabilities = new ChartCapabilities(objectResponse.chartCapabilities.supportedChartTypes, objectResponse.chartCapabilities.supportedChartIntervals, objectResponse.chartCapabilities.supportedSampleIntervals);
		var cameraCapabilities = new CameraCapabilities(objectResponse.cameraCapabilities.supportedInterfaces, objectResponse.cameraCapabilities.supportedWatchdogIntervals);
		var notificationsCapabilities = new NotificationsCapabilities(objectResponse.notificationsCapabilities.supportedNotificationsTypes);
		var isTelegramConnected = objectResponse.telegramConnected;
		return new SystemCapabilities(transductorsCapabilities, mqttInterfaceCapabilities, chartCapabilities, cameraCapabilities, notificationsCapabilities, ruleCapabilities, isTelegramConnected);
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
		RestAPIService.post(systemBaseUri.concat("powerOff/")).then(function() {
			deferred.resolve();
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

});

