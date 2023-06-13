var sweetAlertService = angular.module('sweetAlertService',  []);

sweetAlertService.service('SweetAlertService', function ($location, $route) {
    var self = this;	
    
	self.showSuccessAlertAndRedirect = function (message, redirectURL) {
		this.showSuccessAlert(message).then(function() {
             $location.url(redirectURL);
             $route.reload();
          });
	}
	
	
	self.showSuccessAlert = function (message) {
		return Swal.fire({
	        title: "Done!",
            text: message,
            showConfirmButton: false,
            timer: 5000,
            type: "success"
	      })
	}
	

	self.showErrorAlert = function (message) {
		Swal.fire({
	        title: message,
	        customClass: {
	          confirmButton: 'btn btn-danger'
	        },
	        buttonsStyling: false
	      })
	}
	
	
	self.showWarningWithCallback = function (message, callback) {
		Swal.fire({
	        title: 'Are you sure?',
                    text: message,
                    type: 'warning',
                    showCancelButton: true,
                    confirmButtonClass: 'btn btn-success btn-fill',
                    cancelButtonClass: 'btn btn-danger btn-fill',
                    confirmButtonText: 'Yes, do it!',
                    buttonsStyling: false
	      }).then((result) => {
  				if (result.value) {
    				callback();
  				} 
		})
	}
	
	
	
});
