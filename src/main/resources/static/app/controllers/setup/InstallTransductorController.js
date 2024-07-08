var installTransductorController= angular.module('installTransductorController', ['iotAssistantAPIService', 'sweetAlertService']);

installTransductorController.controller ("InstallTransductorController",function(IotAssistantAPIService, SweetAlertService, $route){

	var self = this;

	self.transductor = new NewTransductor();
	
	self.iotAssistantAPIService = IotAssistantAPIService;

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
		self.transductorType =  this.transductorType;
		promise.then(function() {
			var redirectURL = $route.current.$$route.paramExample;
			SweetAlertService.showSuccessAlertAndRedirect(self.transductorType + ' installed with success', redirectURL);
		},function(error) {
			SweetAlertService.showErrorAlert(self.transductorType + ' installation failed' + ' \n Error: ' + error.data.message);
		})
	}

});
