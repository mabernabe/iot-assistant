var installTransductorController= angular.module('installTransductorController', ['stationAPIService', 'sweetAlertService']);

installTransductorController.controller ("InstallTransductorController",function(StationAPIService, SweetAlertService, $route){

	var self = this;

	self.stationCapabilities = new StationCapabilities();

	self.transductor = new NewTransductor();

	self.selectedPinId;
	
	self.pinInterfaceConfiguration = new Map();

	var getStationCapabilities = function(){
		StationAPIService.getCapabilities()
		.then(function(stationCapabilities) { 
			self.stationCapabilities = stationCapabilities;
		},function() {
		})
	}

	self.getSupportedProperties = function() {
		return this.getTransductorSupportedProperties(self.stationCapabilities);
	}
	
	self.getSupportedWatchdogIntervals = function() {
		return this.getTransductorSupportedWatchdogIntervals(self.stationCapabilities);
	}

	self.getSupportedInterfaces = function() {
		return this.getTransductorSupportedInterfaces(self.stationCapabilities);
	}

	self.setTransductorInterface = function(interfaceType) {
		self.transductor.setInterfaceType(interfaceType);
	}

	self.isPinAvailable = function(propertyName) {
		return this.isTransductorPinAvailable(self.stationCapabilities, propertyName);
	}

	self.getAvailablePinIds = function(propertyName) {
		return this.getTransductorAvailablePinIds(self.stationCapabilities, propertyName);
	}

	self.setPin = function(propertyName) {
		self.pinInterfaceConfiguration.set(propertyName, this.selectedPinId[0]);
	}
	
	self.getPlatformPinInterfaceName = function() {
		return self.stationCapabilities.getPlatformPinInterfaceName();
	}
	
	self.allRequired = function() {
		var interfaceDataIsSet = false;
		if (self.transductor.interfaceTypeIsPIN()) {
			var transductorPinInterface = new TransductorPinInterface(self.pinInterfaceConfiguration);
			interfaceDataIsSet = transductorPinInterface.isValid(self.transductor.getPropertiesNames());
		}
		if (self.transductor.interfaceTypeIsMQTT()) {
			interfaceDataIsSet = true;
		}
		var transductorDataIsSet = (!(self.transductor.getName() == null) && !(self.transductor.getDescription() == null) && !(self.transductor.getWatchdogInterval() == null));
		return (transductorDataIsSet && interfaceDataIsSet) 
	}


	self.installAndRedirect = function(withSwal) {
		var promise;
		if (self.transductor.interfaceTypeIsPIN()) {
			var transductorPinInterface = new TransductorPinInterface(self.pinInterfaceConfiguration);
		    promise = this.installPinInterfaceTransductor(self.transductor, transductorPinInterface);
		}
		if (self.transductor.interfaceTypeIsMQTT()) {
			promise = this.installMQttInterfaceTransductor(self.transductor);
		}
		self.transductorType =  this.getTransductorType();
		promise.then(function() {
			var redirectURL = $route.current.$$route.paramExample;
			SweetAlertService.showSuccessAlertAndRedirect(self.transductorType + ' installed with success', redirectURL);
		},function(error) {
			SweetAlertService.showErrorAlert(self.transductorType + ' installation failed' + ' \n Error: ' + error.data.message);
		})
	}


	getStationCapabilities();


});
