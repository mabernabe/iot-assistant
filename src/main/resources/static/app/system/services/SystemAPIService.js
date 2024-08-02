
systemModule.service ("SystemAPIService",function(RestAPIService, $q){
	let self = this;	
	
	let systemBaseUri = "system/";
	
	self.getSystem = function () {
		let deferred = $q.defer();
		RestAPIService.get(systemBaseUri).then(function(objectResponse) {
			deferred.resolve(getSystemFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.getCapabilities = function () {
		let deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("capabilities/")).then(function(objectResponse) {
			deferred.resolve(getCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.getSensorsCapabilities = function () {
		let deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("sensors-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getTransductorCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function getTransductorCapabilitiesFromResponse(objectResponse) {
		let transductorProperties = getPropertiesFromPropertiesObject(objectResponse.sensorCapabilities.supportedProperties);
		return TransductorCapabilities(transductorProperties, objectResponse.sensorCapabilities.supportedInterfaces, objectResponse.sensorCapabilities.supportedWatchdogIntervals);	
	}
	
	self.getActuatorsCapabilities = function () {
		let deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("actuators-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getTransductorCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.getGpsesCapabilities = function () {
		let deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("gpses-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getGpsesCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function getGpsesCapabilitiesFromResponse(objectResponse) {
		new GpsCapabilities(objectResponse.supportedInterfaces, objectResponse.supportedWatchdogIntervals);	
	}
	
	self.getCamerasCapabilities = function () {
		let deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("cameras-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getCamerasCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function getCamerasCapabilitiesFromResponse(objectResponse) {
		new CameraCapabilities(objectResponse.supportedInterfaces, objectResponse.supportedWatchdogIntervals);	
	}
	
	self.getDevicesCapabilities = function () {
		let deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("devices-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getDevicesCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function getDevicesCapabilitiesFromResponse(objectResponse) {
		let sensorCapabilities = getTransductorCapabilitiesFromResponse(objectResponse.sensorCapabilities);
		let actuatorCapabilities = getTransductorCapabilitiesFromResponse(objectResponse.actuatorCapabilities);
		let cameraCapabilities = getCamerasCapabilitiesFromResponse(objectResponse.cameraCapabilities);
		return new DevicesCapabilities(sensorCapabilities, actuatorCapabilities, cameraCapabilities);
		
	}
	
	self.getRulesCapabilities = function () {
		let deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("rules-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getRulesCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.getNotificationsCapabilities = function () {
		let deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("notifications-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getNotificationsCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	
	
	self.getChartCapabilities = function () {
		let deferred = $q.defer();
		RestAPIService.get(systemBaseUri.concat("charts-capabilities/")).then(function(objectResponse) {
			deferred.resolve(getChartsCapabilitiesFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function getChartsCapabilitiesFromResponse(objectResponse) {
		return new ChartCapabilities(objectResponse.supportedChartTypes, objectResponse.supportedChartIntervals, objectResponse.supportedSampleIntervals);	
	} 
	
	function getSystemFromResponse(objectResponse) {
		let systemCapabilities = getCapabilitiesFromResponse(objectResponse.capabilities);
		return new System(systemCapabilities, objectResponse.platformName, objectResponse.uptime);
	} 
	
	function getCapabilitiesFromResponse(objectResponse) {
		let devicesCapabilities = getDevicesCapabilitiesFromResponse(objectResponse.devicesCapabilities);
		let serversStatus = getServerStatusFromResponse(objectResponse.serversStatus);
		let rulesCapabilities = getRulesCapabilitiesFromResponse(objectResponse.ruleCapabilities);
		let chartCapabilities = getChartsCapabilitiesFromResponse(objectResponse.chartCapabilities);
		let notificationsCapabilities = getNotificationsCapabilitiesFromResponse(objectResponse.notificationsCapabilities);
		return new SystemCapabilities(devicesCapabilities, serversStatus, chartCapabilities, notificationsCapabilities, rulesCapabilities);
	} 
	
	
	function getRulesCapabilitiesFromResponse(objectResponse) {
		return 	new RuleCapabilities(objectResponse.supportedSensorRulesTypes, objectResponse.supportedSensorRulesTimeBetweenTriggers);
	}
	
	function getNotificationsCapabilitiesFromResponse(objectResponse) {
		return 	new NotificationsCapabilities(objectResponse.supportedNotificationsTypes);
	}
	
	
	function getServerStatusFromResponse(objectResponse) {
		let serversStatus = [];
		objectResponse.serversStatus.forEach(serverStatusObject => {
			let serverStatus = new ServerStatus(serverStatusObject.interfaceName, serverStatusObject.connected, serverStatusObject.detail);
			serversStatus.push(serverStatus);
		})
		return serversStatus;
		
	}
	
	function getPropertiesFromPropertiesObject(propertiesObject) {
		let properties = [];
		propertiesObject.forEach(propertyObject => {
			let property = new Property(propertyObject.name, propertyObject.nameWithUnit, propertyObject.unit, propertyObject.digital, propertyObject.minimumValue, propertyObject.maximumValue);
			properties.push(property);
		})
		return properties;
	}
	

	self.powerOff = function () {
		let deferred = $q.defer();
		RestAPIService.post(systemBaseUri.concat("powerOff/")).then(function() {
			deferred.resolve();
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

});

