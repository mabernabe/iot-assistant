
actuatorsModule.service ("ActuatorAPIService",function(RestAPIService, $q){
	let self = this;	
	
	let actuatorsBaseUri = "actuators/";

	self.getActuators = function () {
		let deferred = $q.defer();
		RestAPIService.get(actuatorsBaseUri).then(function(objectResponse) {
			deferred.resolve(getActuatorsFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

	function getActuatorsFromResponse(objectResponse) {
		let actuators = [];
		objectResponse.actuators.forEach(actuatorObject => {
			let actuatorValues = null;		
			if (actuatorObject.active) {
				let values = [];
				for (const [propertyActuated, value] of Object.entries(actuatorObject.actuatorValues.values)) {
					values[propertyActuated] = new ActuatorValue(value.string, value.unit);
				}
				actuatorValues = new ActuatorValues(actuatorObject.actuatorValues.date, values);
			}
			let propertiesActuated = [];
			actuatorObject.propertiesActuated.forEach(propertyActuatedObject => {
				let propertyActuated = new Property(propertyActuatedObject.name, propertyActuatedObject.unit, propertyActuatedObject.binary, propertyActuatedObject.minimumValue, propertyActuatedObject.maximumValue);
				propertiesActuated.push(propertyActuated);
			})
			let actuator = new Actuator(actuatorObject.name, actuatorObject.description, actuatorObject.active, actuatorValues, propertiesActuated, actuatorObject.watchdogInterval, actuatorObject.watchdogEnabled);
			actuators.push(actuator);
		})
		return actuators;
	}
	
	self.setActuatorValue = function (actuator, propertyActuated, newValue) {
		let deferred = $q.defer();
		let newActuatorValue = createSetActuatorValueObjRequest(propertyActuated, newValue);
		RestAPIService.patch(actuatorsBaseUri.concat(actuator.getName()), newActuatorValue).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createSetActuatorValueObjRequest(propertyActuated, newValue) {
		let newActuatorValue = {};
		newActuatorValue.propertyActuated = propertyActuated.getNameWithUnit();
		newActuatorValue.value = newValue;
		return newActuatorValue;
	}
	
	
	self.installPinInterfaceActuator = function (newTransductor, transductorPinInterface) {
		let deferred = $q.defer();
		let newPinInterfaceActuator= createNewPinInterfaceActuatorObjRequest(newTransductor, transductorPinInterface);
		RestAPIService.post(actuatorsBaseUri.concat("pinInterfaceActuators/"), newPinInterfaceActuator).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewPinInterfaceActuatorObjRequest(newActuator, transductorPinInterface) {
		let newPinInterfaceActuator = createActuatorObjectRequest(newActuator);
		newPinInterfaceActuator.pinsConfiguration = Object.fromEntries(transductorPinInterface.getPinsConfiguration());
		return newPinInterfaceActuator;
	}
	
	function createActuatorObjectRequest(newActuator) {
		let newActuatorObject = {};
		newActuatorObject.name = newActuator.getName();
		newActuatorObject.description = newActuator.getDescription();
		newActuatorObject.watchdogInterval = newActuator.getWatchdogInterval();
		return newActuatorObject;
	}
	
	self.installMQttInterfaceActuator = function (newActuator) {
		let deferred = $q.defer();
		let newMqttInterfaceActuator = createNewMqttInterfaceActuatorObjRequest(newActuator);
		RestAPIService.post(actuatorsBaseUri.concat("mqttInterfaceActuators/"), newMqttInterfaceActuator).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewMqttInterfaceActuatorObjRequest(newActuator) {
		let newMqttInterfaceActuator = createActuatorObjectRequest(newActuator);
		newMqttInterfaceActuator.propertiesActuated = newActuator.getPropertiesNames();
		return newMqttInterfaceActuator;
	}
	
	self.deleteActuator = function (name) {
		let deferred = $q.defer();
		RestAPIService.delete(actuatorsBaseUri.concat(name)).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.enableWatchdog = function (enable, actuatorName) {
		let deferred = $q.defer();
		let watchdogEnableRequestObject = {};
		watchdogEnableRequestObject.enable = enable;
		RestAPIService.patch(actuatorsBaseUri.concat(actuatorName + '/watchdog'), watchdogEnableRequestObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}


});

