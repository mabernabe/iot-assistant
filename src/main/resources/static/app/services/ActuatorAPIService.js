var actuatorAPIService = angular.module('actuatorAPIService', ['restAPIService']);

actuatorAPIService.service ("ActuatorAPIService",function(RestAPIService, $q){
	var self = this;	
	
	var actuatorsBaseUri = "actuators/";

	self.getActuators = function () {
		var deferred = $q.defer();
		RestAPIService.get(actuatorsBaseUri).then(function(objectResponse) {
			deferred.resolve(getActuatorsFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

	function getActuatorsFromResponse(objectResponse) {
		var actuators = [];
		objectResponse.actuators.forEach(actuatorObject => {
			var values = [];
			actuatorObject.values.forEach(valueObject  => {
				var value = new ActuatorValue(valueObject.propertyActuated, valueObject.value, valueObject.unit, valueObject.date);
				values.push(value);
			})
			var propertiesActuated = [];
			actuatorObject.propertiesActuated.forEach(propertyActuatedObject => {
				var propertyActuated = new Property(propertyActuatedObject.name, propertyActuatedObject.unit, propertyActuatedObject.digital, propertyActuatedObject.minimumValue, propertyActuatedObject.maximumValue);
				propertiesActuated.push(propertyActuated);
			})
			var actuator = new Actuator(actuatorObject.name, actuatorObject.description, actuatorObject.active, values, propertiesActuated, actuatorObject.watchdogInterval, actuatorObject.watchdogEnabled);
			actuators.push(actuator);
		})
		return actuators;
	}
	
	self.setActuatorValue = function (actuator, value) {
		var deferred = $q.defer();
		var newActuatorValue = createSetActuatorValueObjRequest(value);
		RestAPIService.patch(actuatorsBaseUri.concat(actuator.getName()), newActuatorValue).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createSetActuatorValueObjRequest(value) {
		var newActuatorValue = {};
		newActuatorValue.propertyActuated = value.property;
		newActuatorValue.value = value.value;
		newActuatorValue.unit = value.unit;
		return newActuatorValue;
	}
	
	
	self.installPinInterfaceActuator = function (newTransductor, transductorPinInterface) {
		var deferred = $q.defer();
		var newPinInterfaceActuator= createNewPinInterfaceActuatorObjRequest(newTransductor, transductorPinInterface);
		RestAPIService.post(actuatorsBaseUri.concat("pinInterfaceActuators/"), newPinInterfaceActuator).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewPinInterfaceActuatorObjRequest(newActuator, transductorPinInterface) {
		var newPinInterfaceActuator = createActuatorObjectRequest(newActuator);
		newPinInterfaceActuator.pinsConfiguration = Object.fromEntries(transductorPinInterface.getPinsConfiguration());
		return newPinInterfaceActuator;
	}
	
	function createActuatorObjectRequest(newActuator) {
		var newActuatorObject = {};
		newActuatorObject.name = newActuator.getName();
		newActuatorObject.description = newActuator.getDescription();
		newActuatorObject.watchdogInterval = newActuator.getWatchdogInterval();
		return newActuatorObject;
	}
	
	self.installMQttInterfaceActuator = function (newActuator) {
		var deferred = $q.defer();
		var newMqttInterfaceActuator = createNewMqttInterfaceActuatorObjRequest(newActuator);
		RestAPIService.post(actuatorsBaseUri.concat("mqttInterfaceActuators/"), newMqttInterfaceActuator).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewMqttInterfaceActuatorObjRequest(newActuator) {
		var newMqttInterfaceActuator = createActuatorObjectRequest(newActuator);
		newMqttInterfaceActuator.propertiesActuated = newActuator.getPropertiesNames();
		return newMqttInterfaceActuator;
	}
	
	self.deleteActuator = function (name) {
		var deferred = $q.defer();
		RestAPIService.delete(actuatorsBaseUri.concat(name)).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.enableWatchdog = function (enable, actuatorName) {
		var deferred = $q.defer();
		var watchdogEnableRequestObject = {};
		watchdogEnableRequestObject.enable = enable;
		RestAPIService.patch(actuatorsBaseUri.concat(actuatorName + '/watchdog'), watchdogEnableRequestObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}


});

