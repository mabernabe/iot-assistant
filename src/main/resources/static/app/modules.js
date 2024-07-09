let devicesModule = angular.module("devicesModule",['sensorAPIService', 'actuatorAPIService', 'cameraAPIService', 'iotAssistantAPIService', 'sweetAlertService'] );
let sensorRulesModule = angular.module("sensorRulesModule",['sensorRuleAPIService', 'actuatorAPIService', 'sensorAPIService', 'cameraAPIService', 'iotAssistantAPIService', 'sweetAlertService'] );
let chartsModule = angular.module("chartsModule",['chartAPIService', 'sweetAlertService', 'chartsDrawService'] );
