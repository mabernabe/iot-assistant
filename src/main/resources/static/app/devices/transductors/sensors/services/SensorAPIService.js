
sensorsModule.service ("SensorAPIService",function(RestAPIService, $q){
	let self = this;	
	
	let sensorsBaseUri = "sensors/";

	self.getSensors = function () {
		let deferred = $q.defer();
		RestAPIService.get(sensorsBaseUri).then(function(objectResponse) {
			deferred.resolve(getSensorsFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	

	function getSensorsFromResponse(objectResponse) {
		let sensors = [];
		objectResponse.sensors.forEach(sensorObject => {
			let propertiesMeasured = [];
			sensorObject.propertiesMeasured.forEach(PropertyMeasuredObject => {
				let propertyMeasured = new Property(PropertyMeasuredObject.name, PropertyMeasuredObject.unit, PropertyMeasuredObject.binary, PropertyMeasuredObject.minimumValue, PropertyMeasuredObject.maximumValue);
				propertiesMeasured.push(propertyMeasured);
			})
			let sensorValues = null;		
			if (sensorObject.active) {
				let values = [];
				for (const [propertyMeasured, value] of Object.entries(sensorObject.sensorValues.values)) {
					values[propertyMeasured] = new SensorValue(value.string, value.unit, value.description, value.severity);
				}
				sensorValues = new SensorValues(sensorObject.sensorValues.date, values);
			}
			let sensor = new Sensor(sensorObject.name, sensorObject.description, sensorObject.active, sensorValues, propertiesMeasured, sensorObject.watchdogInterval, sensorObject.watchdogEnabled);
			sensors.push(sensor);
		})
		return sensors;
	}
	
	
	self.installMQttInterfaceSensor = function (newSensor) {
		let deferred = $q.defer();
		let newMqttInterfaceSensor = createNewMqttInterfaceSensorObjRequest(newSensor);
		RestAPIService.post(sensorsBaseUri.concat("mqttInterfaceSensors/"), newMqttInterfaceSensor).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewMqttInterfaceSensorObjRequest(newSensor) {
		let newMqttInterfaceSensor = createSensorObjectRequest(newSensor);
		newMqttInterfaceSensor.propertiesMeasured = newSensor.getPropertiesNames();
		return newMqttInterfaceSensor;
	}
	
		function createSensorObjectRequest(newSensor) {
		let newSensorObject = {};
		newSensorObject.name = newSensor.getName();
		newSensorObject.description = newSensor.getDescription();
		newSensorObject.watchdogInterval = newSensor.getWatchdogInterval();
		return newSensorObject;
	}
	
	self.deleteSensor = function (name) {
		let deferred = $q.defer();
		RestAPIService.delete(sensorsBaseUri.concat(name)).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.enableWatchdog = function (enable, sensorName) {
		let deferred = $q.defer();
		let watchdogEnableRequestObject = {};
		watchdogEnableRequestObject.enable = enable;
		RestAPIService.patch(sensorsBaseUri.concat(sensorName), watchdogEnableRequestObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	

});

