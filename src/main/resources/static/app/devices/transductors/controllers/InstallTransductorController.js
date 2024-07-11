
transductorsModule.controller ("InstallTransductorController",function(IotAssistantAPIService, SweetAlertService, $route){

	let self = this;

	self.transductor = new NewTransductor();
	
	self.iotAssistantAPIService = IotAssistantAPIService;

	self.setTransductorInterface = function(interfaceType) {
		self.transductor.setInterfaceType(interfaceType);
	}

	self.allRequired = function() {
		let interfaceDataIsSet = false;
		if (self.transductor.interfaceTypeIsMQTT()) {
			interfaceDataIsSet = true;
		}
		let transductorDataIsSet = (!(self.transductor.getName() == null) && !(self.transductor.getDescription() == null) && !(self.transductor.getWatchdogInterval() == null));
		return (transductorDataIsSet && interfaceDataIsSet) 
	}

	self.installAndRedirect = function(withSwal) {
		let promise;
		if (self.transductor.interfaceTypeIsMQTT()) {
			promise = this.installMQttInterfaceTransductor(self.transductor);
		}
		self.transductorType =  this.transductorType;
		promise.then(function() {
			let redirectURL = $route.current.$$route.paramExample;
			SweetAlertService.showSuccessAlertAndRedirect(self.transductorType + ' installed with success', redirectURL);
		},function(error) {
			SweetAlertService.showErrorAlert(self.transductorType + ' installation failed' + ' \n Error: ' + error.data.message);
		})
	}

});
