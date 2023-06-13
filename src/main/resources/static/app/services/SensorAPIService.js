var sensorAPIService = angular.module('sensorAPIService', ['restAPIService']);

sensorAPIService.service ("SensorAPIService",function(RestAPIService, $q){
	var self = this;	
	
	var sensorsBaseUri = "sensors/";

	self.getSensors = function () {
		var deferred = $q.defer();
		RestAPIService.get(sensorsBaseUri).then(function(objectResponse) {
			deferred.resolve(getSensorsFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	

	function getSensorsFromResponse(objectResponse) {
		var sensors = [];
		objectResponse.sensors.forEach(sensorObject => {
			var measures = [];
			sensorObject.measures.forEach(measureObject  => {
				var measure = new Measure(measureObject.propertyMeasured, measureObject.value, measureObject.unit, measureObject.date, measureObject.severity, measureObject.valueDescription);
				measures.push(measure);
			})
			var properties = [];
			sensorObject.properties.forEach(propertyObject => {
				var property = new Property(propertyObject.name, propertyObject.shortName, propertyObject.digital, propertyObject.minimumValue, propertyObject.maximumValue);
				properties.push(property);
			})
			var sensor = new Sensor(measures, sensorObject.name, sensorObject.description, properties, sensorObject.watchdogInterval, sensorObject.watchdogEnabled);
			sensors.push(sensor);
		})
		return sensors;
	}
	
	self.installPinInterfaceSensor = function (newSensor, sensorPinInterface) {
		var deferred = $q.defer();
		var newPinInterfaceSensor = createNewPinInterfaceSensorObjRequest(newSensor, sensorPinInterface);
		RestAPIService.post(sensorsBaseUri.concat("pinInterfaceSensors/"), newPinInterfaceSensor).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewPinInterfaceSensorObjRequest(newSensor, sensorPinInterface) {
		var newPinInterfaceSensor = createSensorObjectRequest(newSensor);
		newPinInterfaceSensor.pinsConfiguration = Object.fromEntries(sensorPinInterface.getPinsConfiguration());
		return newPinInterfaceSensor;
	}
	
	function createSensorObjectRequest(newSensor) {
		var newSensorObject = {};
		newSensorObject.name = newSensor.getName();
		newSensorObject.description = newSensor.getDescription();
		newSensorObject.watchdogInterval = newSensor.getWatchdogInterval();
		return newSensorObject;
	}
	
	self.installMQttInterfaceSensor = function (newSensor) {
		var deferred = $q.defer();
		var newMqttInterfaceSensor = createNewMqttInterfaceSensorObjRequest(newSensor);
		RestAPIService.post(sensorsBaseUri.concat("mqttInterfaceSensors/"), newMqttInterfaceSensor).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewMqttInterfaceSensorObjRequest(newSensor) {
		var newMqttInterfaceSensor = createSensorObjectRequest(newSensor);
		newMqttInterfaceSensor.propertiesMeasured = newSensor.getPropertiesNames();
		return newMqttInterfaceSensor;
	}
	
	self.deleteSensor = function (name) {
		var deferred = $q.defer();
		RestAPIService.delete(sensorsBaseUri.concat(name)).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.enableWatchdog = function (enable, sensorName) {
		var deferred = $q.defer();
		var watchdogEnableRequestObject = {};
		watchdogEnableRequestObject.enable = enable;
		RestAPIService.patch(sensorsBaseUri.concat(sensorName), watchdogEnableRequestObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	

});

