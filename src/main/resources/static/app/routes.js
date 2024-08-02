let sensorsRoute = '/sensors';
let actuatorsRoute = '/actuators';
let camerasRoute = '/cameras';
let gpsesRoute = '/gpses';
let notificationsRoute = '/notifications';
let chartsRoute = '/charts';
let sensorRulesRoute = '/sensorRules';
let installSensorRoute = '/installSensor';
let installActuatorRoute = '/installActuator';
let installChartRoute = '/installChart';
let installGpsRoute = '/installGps';
let installCameraRoute = '/installCamera';
let installSensorRuleRoute = '/installSensorRule';
let systemInformationRoute = '/systemInformation';
let systemPowerOffRoute = '/systemPowerOff';


iotAssistant.config(function($routeProvider) {
	$routeProvider
	.when('/', {
		templateUrl : '../devices/transductors/sensors/sensors.html',
		controller : 'GetSensorsController'
	})
	.when(sensorsRoute, {
		templateUrl : '../devices/transductors/sensors/sensors.html',
		controller : 'GetSensorsController'
	})
	.when(actuatorsRoute, {
		templateUrl : '../devices/transductors/actuators/actuators.html',
		controller : 'GetActuatorsController'
	})
	.when(camerasRoute, {
		templateUrl : '../devices/cameras/cameras.html',
		controller : 'GetCamerasController',
	})	
	.when(gpsesRoute, {
		templateUrl : '../devices/gps/gpses.html',
		controller : 'GetGpsesController'
	})	
	.when(notificationsRoute, {
		templateUrl : '../notifications/notifications.html',
		controller : 'GetNotificationsController'
	})
	.when(chartsRoute, {
		templateUrl : '../charts/charts.html',
		controller : 'GetChartsController'
	})
	.when(sensorRulesRoute, {
		templateUrl : '../sensorrules/sensorrules.html',
		controller : 'GetSensorRulesController'
	})
	.when(installSensorRoute, {
		templateUrl : '../devices/transductors/transductorInstallation.html',
		controller : 'InstallSensorController',
		controllerAs: "InstallTransductorController",
		paramExample: sensorsRoute
	})
	.when(installActuatorRoute, {
		templateUrl : '../devices/transductors/transductorInstallation.html',
		controller : 'InstallActuatorController',
		controllerAs: "InstallTransductorController",
		paramExample: actuatorsRoute
	})
	.when(installGpsRoute, {
		templateUrl : '../devices/gpses/gpsInstallation.html',
		controller : 'InstallGpsController',
		paramExample: gpsesRoute
	})
	.when(installChartRoute, {
		templateUrl : '../charts/chartInstallation.html',
		controller : 'InstallChartController',
		paramExample: chartsRoute
	})
	.when(installSensorRuleRoute, {
		templateUrl : '../sensorrules/sensorRuleInstallation.html',
		controller : 'InstallSensorRuleController',
		paramExample: sensorRulesRoute
	})
	.when(installCameraRoute, {
		templateUrl : '../devices/cameras/cameraInstallation.html',
		controller : 'InstallCameraController',
		paramExample: camerasRoute
	})
	.when(systemInformationRoute, {
		templateUrl : '../system/systemInformation.html',
		controller : 'GetSystemInfoController',
	})
	.when(systemPowerOffRoute, {
		templateUrl : '../system/systemPowerOff.html',
		controller : 'PowerOffSystemController',
	})
});