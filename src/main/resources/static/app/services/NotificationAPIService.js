var notificationAPIService = angular.module('notificationAPIService', ['restAPIService']);

notificationAPIService.service ("NotificationAPIService",function(RestAPIService, $q){
	
	var self = this;	
	
	var notificationsBaseUri = "notifications/";
	
	var notificationsMapper = new NotificationsMapper();
	
	self.getNotifications = function () {
		var deferred = $q.defer();
		RestAPIService.get(notificationsBaseUri).then(function(notificationsServiceObject) {
			deferred.resolve(notificationsMapper.buildNotificationsFromServiceObject(notificationsServiceObject));
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	
	self.deleteNotification = function (notificationId) {
		var deferred = $q.defer();
		RestAPIService.delete(notificationsBaseUri.concat(notificationId)).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}
	
	self.deleteNotifications = function () {
		var deferred = $q.defer();
		RestAPIService.delete(notificationsBaseUri).then(function(objectResponse) {
			deferred.resolve(objectResponse);
		}, function errorCallback(errorResponse) {
			deferred.reject(errorResponse);
		});
		return deferred.promise ;
	}

});

