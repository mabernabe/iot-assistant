
gpsesModule.service ("GpsAPIService",function(RestAPIService, $q){
	var self = this;	
	
	var gpsBaseUri = "gpss/";

	self.getGPSes = function () {
		var deferred = $q.defer();
		RestAPIService.get(gpsBaseUri).then(function(objectResponse) {
			deferred.resolve(getGPSsFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

	function getGPSsFromResponse(objectResponse) {
		var gpss = [];
		objectResponse.gpss.forEach(gpsObject => {
			var gps = new GPS(gpsObject.name, gpsObject.description, gpsObject.active, gpsObject.watchdogInterval, gpsObject.watchdogEnabled, gpsObject.longitude, gpsObject.latitude);
			gpss.push(gps);
		})
		return gpss;
	}
	
	
	self.installMqttGps = function (mqttGPS) {
		var deferred = $q.defer();
		var newMQTTGPSObjRequest= createNewMQTTGPSObjRequest(mqttGPS);
		RestAPIService.post(gpsBaseUri.concat("mqtt-interface-gpses/"), newMQTTGPSObjRequest).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewMQTTGPSObjRequest(mqttGPS) {
		var newMQTTGPSObjRequest = {};
		newMQTTGPSObjRequest.name = mqttGPS.getName();
		newMQTTGPSObjRequest.description = mqttGPS.getDescription();
		newMQTTGPSObjRequest.watchdogInterval = mqttGPS.getWatchdogInterval();
		return newMQTTGPSObjRequest;
	}
	

	self.deleteGPS = function (name) {
		var deferred = $q.defer();
		RestAPIService.delete(gpsBaseUri.concat(name)).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.enableWatchdog = function (enable, gpsName) {
		var deferred = $q.defer();
		var watchdogEnableRequestObject = {};
		watchdogEnableRequestObject.enable = enable;
		RestAPIService.patch(gpsBaseUri.concat(gpsName + '/watchdog'), watchdogEnableRequestObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}


});

