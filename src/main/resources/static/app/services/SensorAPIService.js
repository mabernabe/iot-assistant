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
			var propertiesMeasured = [];
			sensorObject.propertiesMeasured.forEach(PropertyMeasuredObject => {
				var propertyMeasured = new Property(PropertyMeasuredObject.name, PropertyMeasuredObject.unit, PropertyMeasuredObject.shortName, PropertyMeasuredObject.digital, PropertyMeasuredObject.minimumValue, PropertyMeasuredObject.maximumValue);
				propertiesMeasured.push(propertyMeasured);
			})
			sensorValues = null;		
			if (sensorObject.active) {
				let values = [];
				for (const [propertyMeasured, value] of Object.entries(sensorObject.sensorValues.values)) {
					values[propertyMeasured] = new SensorValue(value.string, value.unit, value.description, value.severity);
				}
				sensorValues = new SensorValues(sensorObject.sensorValues.date, values);
			}
			var sensor = new Sensor(sensorObject.name, sensorObject.description, sensorObject.active, sensorValues, propertiesMeasured, sensorObject.watchdogInterval, sensorObject.watchdogEnabled);
			sensors.push(sensor);
		})
		return sensors;
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
	
		function createSensorObjectRequest(newSensor) {
		var newSensorObject = {};
		newSensorObject.name = newSensor.getName();
		newSensorObject.description = newSensor.getDescription();
		newSensorObject.watchdogInterval = newSensor.getWatchdogInterval();
		return newSensorObject;
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

