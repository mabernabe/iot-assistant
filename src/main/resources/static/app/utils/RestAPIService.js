

utilsModule.config(function($resourceProvider) { 
	$resourceProvider.defaults.stripTrailingSlashes = false; 
})

utilsModule.service ("RestAPIService",function(RestLocationService, $q, $resource){
	var self = this;	


	self.get = function (url, params) {	
		console.log("Get resource " + url );	
		var resource = $resource(RestLocationService.restlocation + url, params);	
		var deferred = $q.defer();
		resource.get().$promise.then(function(response) {
			deferred.resolve(response); 
		}, function(response) {
			deferred.reject(response);
		});
		return deferred.promise ;
	}


	self.post = function (url, data) {
		console.log("Post resource" + url);
		var resource = $resource(RestLocationService.restlocation + url);	
		var deferred = $q.defer();
		resource.save(data).$promise.then(function(response) {
			deferred.resolve(response); 
		}, function(response) {
			deferred.reject(response);
		});
		return deferred.promise ;
	}

	
	self.patch = function (url, data) {
		console.log("Post resource" + url);
		var resource = $resource(RestLocationService.restlocation + url, null, {
			   patch: {method: 'PATCH'}
		});	
		var deferred = $q.defer();
		resource.patch(data).$promise.then(function(response) {
			deferred.resolve(response); 
		}, function(response) {
			deferred.reject(response);
		});
		return deferred.promise ;
	}

	self.delete = function (url, data) {
		console.log("Delete resource" + url);
		var resource = $resource(RestLocationService.restlocation + url);	
		var deferred = $q.defer();
		resource.remove(data).$promise.then(function(response) {
			deferred.resolve(response); 
		}, function(response) {
			deferred.reject(response);
		});
		return deferred.promise ;
	}



});

