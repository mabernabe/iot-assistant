
systemModule.controller("PowerOffSystemController",function(SystemAPIService, SweetAlertService){

	var self = this;
	
	self.powerOff = function(){
		function powerOff() {
			SystemAPIService.powerOff()
			.then(function() { 
				SweetAlertService.showSuccessAlert('System is going to power off now');
			},function(error) {
				SweetAlertService.showErrorAlert('Power off failed' + ' \n Error: ' + error.data.message);
			})
		}
		SweetAlertService.showWarningWithCallback('Are you sure you want to power off the system ?', powerOff);
	}

});
