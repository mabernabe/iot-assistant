var sensorsRoute = '/sensors';
var actuatorsRoute = '/actuators';
var alarmsRoute = '/alarms';
var chartsRoute = '/charts';
var sensorRulesRoute = '/sensorRules';
var camerasRoute = '/cameras';



stationsAssistant.config(function($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl : 'sensors.html',
		controller : 'GetSensorsController',
		controllerAs: "GetSensorsController"
	})
	.when(sensorsRoute, {
		templateUrl : 'sensors.html',
		controller : 'GetSensorsController',
		controllerAs: "GetSensorsController"
	})
	.when(actuatorsRoute, {
		templateUrl : 'actuators.html',
		controller : 'GetActuatorsController',
		controllerAs: "GetActuatorsController"
	})
	.when('/notifications', {
		templateUrl : 'notifications.html',
		controller : 'GetNotificationsController',
		controllerAs: "GetNotificationsController",
	})
	.when(chartsRoute, {
		templateUrl : 'charts.html',
		controller : 'GetChartsController',
		controllerAs: "GetChartsController"
	})
	.when(sensorRulesRoute, {
		templateUrl : 'sensorrules.html',
		controller : 'GetSensorRulesController',
		controllerAs: "GetSensorRulesController"
	})
	.when(camerasRoute, {
		templateUrl : 'cameras.html',
		controller : 'GetCamerasController',
		controllerAs: "GetCamerasController"
	})
	.when('/sensorInstallation', {
		templateUrl : 'setup/transductor/transductorInstallation.html',
		controller : 'InstallSensorController',
		controllerAs: "InstallTransductorController",
		paramExample: sensorsRoute
	})
	.when('/actuatorInstallation', {
		templateUrl : 'setup/transductor/transductorInstallation.html',
		controller : 'InstallActuatorController',
		controllerAs: "InstallTransductorController",
		paramExample: actuatorsRoute
	})
	.when('/chartInstallation', {
		templateUrl : 'setup/chart/chartInstallation.html',
		controller : 'InstallChartController',
		controllerAs: "InstallChartController",
		paramExample: chartsRoute
	})
	.when('/sensorRuleInstallation', {
		templateUrl : 'setup/rules/sensorRuleInstallation.html',
		controller : 'InstallSensorRuleController',
		controllerAs: "InstallSensorRuleController",
		paramExample: sensorRulesRoute
	})
	.when('/cameraInstallation', {
		templateUrl : 'setup/camera/cameraInstallation.html',
		controller : 'InstallCameraController',
		controllerAs: "InstallCameraController",
		paramExample: camerasRoute
	})
	.when('/systemInformation', {
		templateUrl : 'systemInformation.html',
		controller : 'GetSystemInfoController',
		controllerAs: "GetSystemInfoController",
	})
	.when('/systemPowerOff', {
		templateUrl : 'systemPowerOff.html',
		controller : 'PowerOffSystemController',
		controllerAs: "PowerOffSystemController",
	})
});