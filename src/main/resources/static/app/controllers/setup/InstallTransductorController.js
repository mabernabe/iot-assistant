var installTransductorController= angular.module('installTransductorController', ['iotAssistantAPIService', 'sweetAlertService']);

installTransductorController.controller ("InstallTransductorController",function(IotAssistantAPIService, SweetAlertService, $route){

	var self = this;

	self.devicesCapabilities;

	self.transductor = new NewTransductor();

	var getTransductorsCapabilities = function(){
		IotAssistantAPIService.getDevicesCapabilities()
		.then(function(devicesCapabilities) { 
			self.devicesCapabilities = devicesCapabilities;
		},function() {
		})
	}

	self.getSupportedProperties = function() {
		return this.getTransductorSupportedProperties(self.devicesCapabilities);
	}
	
	self.getSupportedWatchdogIntervals = function() {
		return this.getTransductorSupportedWatchdogIntervals(self.devicesCapabilities);
	}

	self.getSupportedInterfaces = function() {
		return this.getTransductorSupportedInterfaces(self.devicesCapabilities);
	}

	self.setTransductorInterface = function(interfaceType) {
		self.transductor.setInterfaceType(interfaceType);
	}

	
	self.allRequired = function() {
		var interfaceDataIsSet = false;
		if (self.transductor.interfaceTypeIsMQTT()) {
			interfaceDataIsSet = true;
		}
		var transductorDataIsSet = (!(self.transductor.getName() == null) && !(self.transductor.getDescription() == null) && !(self.transductor.getWatchdogInterval() == null));
		return (transductorDataIsSet && interfaceDataIsSet) 
	}


	self.installAndRedirect = function(withSwal) {
		var promise;
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


	getTransductorsCapabilities();


});
