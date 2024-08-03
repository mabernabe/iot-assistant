
gpsesModule.service ("GpsAPIService",function(RestAPIService, $q){
	let self = this;	
	
	let gpsBaseUri = "gpses/";

	self.getGPSes = function () {
		let deferred = $q.defer();
		RestAPIService.get(gpsBaseUri).then(function(objectResponse) {
			deferred.resolve(getGPSsFromResponse(objectResponse));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

	function getGPSsFromResponse(objectResponse) {
		let gpss = [];
		objectResponse.gpses.forEach(gpsObject => {
			let position = null;		
			if (gpsObject.active) {
				position = new GpsPosition(gpsObject.longitude, gpsObject.latitude,  gpsObject.date);
			}
			let gps = new Gps(gpsObject.name, gpsObject.description, gpsObject.active, gpsObject.watchdogInterval, gpsObject.watchdogEnabled, position);
			gpss.push(gps);
		})
		return gpss;
	}
	
	
	self.installMqttGps = function (mqttGPS) {
		let deferred = $q.defer();
		let newMQTTGPSObjRequest= createNewMQTTGPSObjRequest(mqttGPS);
		RestAPIService.post(gpsBaseUri.concat("mqtt-interface-gpses/"), newMQTTGPSObjRequest).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	function createNewMQTTGPSObjRequest(mqttGPS) {
		let newMQTTGPSObjRequest = {};
		newMQTTGPSObjRequest.name = mqttGPS.getName();
		newMQTTGPSObjRequest.description = mqttGPS.getDescription();
		newMQTTGPSObjRequest.watchdogInterval = mqttGPS.getWatchdogInterval();
		return newMQTTGPSObjRequest;
	}
	

	self.deleteGPS = function (name) {
		let deferred = $q.defer();
		RestAPIService.delete(gpsBaseUri.concat(name)).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.enableWatchdog = function (enable, gpsName) {
		let deferred = $q.defer();
		let watchdogEnableRequestObject = {};
		watchdogEnableRequestObject.enable = enable;
		RestAPIService.patch(gpsBaseUri.concat(gpsName + '/watchdog'), watchdogEnableRequestObject).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}


});

