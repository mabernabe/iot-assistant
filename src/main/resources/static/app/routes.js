let sensorsRoute = '/sensors';
let actuatorsRoute = '/actuators';
let camerasRoute = '/cameras';
let notificationsRoute = '/notifications';
let chartsRoute = '/charts';
let sensorRulesRoute = '/sensorRules';
let installSensorRoute = '/installSensor';
let installActuatorRoute = '/installActuator';
let installChartRoute = '/installChart';
let installCameraRoute = '/installCamera';
let installSensorRuleRoute = '/installSensorRule';
let systemInformationRoute = '/systemInformation';
let systemPowerOffRoute = '/systemPowerOff';


iotAssistant.config(function($routeProvider) {
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
	.when(notificationsRoute, {
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
	.when(installSensorRoute, {
		templateUrl : 'setup/transductor/transductorInstallation.html',
		controller : 'InstallSensorController',
		controllerAs: "InstallTransductorController",
		paramExample: sensorsRoute
	})
	.when(installActuatorRoute, {
		templateUrl : 'setup/transductor/transductorInstallation.html',
		controller : 'InstallActuatorController',
		controllerAs: "InstallTransductorController",
		paramExample: actuatorsRoute
	})
	.when(installChartRoute, {
		templateUrl : 'setup/chart/chartInstallation.html',
		controller : 'InstallChartController',
		controllerAs: "InstallChartController",
		paramExample: chartsRoute
	})
	.when(installSensorRuleRoute, {
		templateUrl : 'setup/rules/sensorRuleInstallation.html',
		controller : 'InstallSensorRuleController',
		controllerAs: "InstallSensorRuleController",
		paramExample: sensorRulesRoute
	})
	.when(installCameraRoute, {
		templateUrl : 'setup/camera/cameraInstallation.html',
		controller : 'InstallCameraController',
		controllerAs: "InstallCameraController",
		paramExample: camerasRoute
	})
	.when(systemInformationRoute, {
		templateUrl : 'systemInformation.html',
		controller : 'GetIotAssistantController',
		controllerAs: "GetIotAssistantController",
	})
	.when(systemPowerOffRoute, {
		templateUrl : 'systemPowerOff.html',
		controller : 'PowerOffSystemController',
		controllerAs: "PowerOffSystemController",
	})
});