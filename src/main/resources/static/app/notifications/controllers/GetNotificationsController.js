
notificationsModule.controller("GetNotificationsController",function($scope, NotificationAPIService, SweetAlertService, $interval){
	
	const NOTIFICATIONS_REFRESH_TIME_MS = 2000 ;

	let self = this;

	self.notifications = [];
	
	let fetchNotifications = function(){
		NotificationAPIService.getNotifications()
		.then(function(notifications) { 
			self.notifications = notifications.getAllNotificationsSortedByDate();	
		},function() {
			self.notifications = [];
		})
	}
	
		let initializeController = function() {
		fetchNotifications();   
		let refreshNotificationsInterval = $interval(fetchNotifications, NOTIFICATIONS_REFRESH_TIME_MS);
		$scope.$on('$destroy',function(){
			if(refreshNotificationsInterval) {
				$interval.cancel(refreshNotificationsInterval);
			}
		})
	}
	
	initializeController();
	
	self.deleteAllNotifications = function(){
		function deleteNotifications() {
			NotificationAPIService.deleteNotifications()
			.then(function() { 
				SweetAlertService.showSuccessAlert('All notifications has been deleted');
			},function() {
				SweetAlertService.showErrorAlert('Notifications deletion failed');
			})
		}
		SweetAlertService.showWarningWithCallback('Are you sure you want to delete all notifications ?', deleteNotifications);	
	}
	
	self.deleteNotification = function(notificationId){
		NotificationAPIService.deleteNotification(notificationId)
		.then(function() { 
		},function() {
			SweetAlertService.showErrorAlert('Notification deletion failed');
		})	
	}

});
