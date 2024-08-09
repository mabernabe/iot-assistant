package com.iotassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iotassistant.controllers.dtos.AlarmSensorRuleDTO;
import com.iotassistant.controllers.dtos.CameraSensorRuleDTO;
import com.iotassistant.controllers.dtos.EnableRuleSensorRuleDTO;
import com.iotassistant.controllers.dtos.ErrorDTO;
import com.iotassistant.controllers.dtos.SensorRulesDTO;
import com.iotassistant.controllers.dtos.TriggerActuatorSensorRuleDTO;
import com.iotassistant.controllers.dtos.devices.EnableDTO;
import com.iotassistant.models.AlarmSensorRule;
import com.iotassistant.models.CameraSensorRule;
import com.iotassistant.models.EnableRuleSensorRule;
import com.iotassistant.models.SensorRule;
import com.iotassistant.models.TriggerActuatorSensorRule;
import com.iotassistant.services.ActuatorsService;
import com.iotassistant.services.CamerasService;
import com.iotassistant.services.SensorRulesService;
import com.iotassistant.services.SensorsService;

@RestController
@RequestMapping("${rules.uri}")
public class SensorRulesController {
	
	@Autowired
	private SensorRulesService sensorRulesService;
	
	@Autowired
	private SensorsService sensorsService;
	
	@Autowired
	private ActuatorsService actuatorsService;
	
	@Autowired
	private CamerasService camerasService;
	
	@RequestMapping(value="/sensorRules", method = RequestMethod.GET)
	public ResponseEntity<?> getAllSensorRules() {
		SensorRulesDTO sensorRulesDTO = new SensorRulesDTO(sensorRulesService.getAllSensorRules());
		return new ResponseEntity<>(sensorRulesDTO, HttpStatus.CREATED);		
			
	}
	
	@RequestMapping(value="/enableSensorRules/", method = RequestMethod.POST)
	public ResponseEntity<?> newEnableRuleSensorRule(@RequestBody EnableRuleSensorRuleDTO enableRuleSensorRuleDTO) {
		EnableRuleSensorRule enableRuleSensorRule = enableRuleSensorRuleDTO.getSensorRule();
		if (sensorRulesService.getSensorRule(enableRuleSensorRule.getSensorRuleId()) == null) {
			ErrorDTO sensorRuleNotFoundDTO = ErrorDTO.SENSOR_RULE_NOT_FOUND;
			return new ResponseEntity<>(sensorRuleNotFoundDTO, sensorRuleNotFoundDTO.getHttpStatus());
        }
		return this.newSensorRule(enableRuleSensorRule);
	}
	
	private ResponseEntity<?> newSensorRule(SensorRule sensorRule)  {
		ErrorDTO errorDTO = null;
		if (!sensorsService.exist(sensorRule.getSensorName()))  {
			errorDTO = ErrorDTO.DEVICE_NOT_FOUND;
			errorDTO.formatMessage("Sensor");
	    }
		if (sensorRulesService.existSensorRule(sensorRule)) {
			errorDTO = ErrorDTO.SENSOR_RULE_ALREADY_EXIST;
        }
		if (!sensorsService.hasSensorProperty(sensorRule.getSensorName(), sensorRule.getPropertyObserved()))  {
			errorDTO = ErrorDTO.SENSOR_HAS_NOT_PROPERTY;
			errorDTO.formatMessage(sensorRule.getPropertyObserved().toString());
	    }
		if(!sensorRule.getPropertyObserved().isValidValue(sensorRule.getValueThresholdObserved())) {
			errorDTO = ErrorDTO.VALUE_IS_NOT_VALID;
		}
		if (errorDTO != null) {
			return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
		}
		sensorRulesService.newSensorRule(sensorRule);
	    return new ResponseEntity<>(null, HttpStatus.CREATED);
	}

	@RequestMapping(value="/alarmSensorRules/", method = RequestMethod.POST)
	public ResponseEntity<?> newAlarmSensorRule(@RequestBody AlarmSensorRuleDTO alarmSensorRuleDTO)   {
	    AlarmSensorRule sensorRule = alarmSensorRuleDTO.getSensorRule();
	    return this.newSensorRule(sensorRule);
	}
	
	@RequestMapping(value="/sensorRules/{id}", method = RequestMethod.PATCH)
	public ResponseEntity<?> enableRuleById(@PathVariable("id") int id, @RequestBody EnableDTO enableRuleDTO)   {
		if (sensorRulesService.getSensorRule(id) == null) {
			ErrorDTO sensorRuleNotFoundDTO = ErrorDTO.SENSOR_RULE_NOT_FOUND;
			return new ResponseEntity<>(sensorRuleNotFoundDTO, sensorRuleNotFoundDTO.getHttpStatus());
        }	
		sensorRulesService.enableDisableRule(enableRuleDTO.isEnable(), id);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}


	@RequestMapping(value="/sensorRules/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteRuleById(@PathVariable("id") int id)   {
		if (sensorRulesService.getSensorRule(id) == null) {
			ErrorDTO sensorRuleNotFoundDTO = ErrorDTO.SENSOR_RULE_NOT_FOUND;
			return new ResponseEntity<>(sensorRuleNotFoundDTO, sensorRuleNotFoundDTO.getHttpStatus());
        }
		sensorRulesService.deleteSensorRuleById(id);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}


	@RequestMapping(value="/triggerActuatorSensorRules/", method = RequestMethod.POST)
	public ResponseEntity<?> newTriggerActuatorSensorRule(@RequestBody TriggerActuatorSensorRuleDTO triggerActuatorSensorRuleDTO)     {
		TriggerActuatorSensorRule sensorRule = triggerActuatorSensorRuleDTO.getSensorRule();
		ErrorDTO errorDTO = null;
		if (!actuatorsService.existActuator(sensorRule.getActuatorName())) {
			errorDTO = ErrorDTO.DEVICE_NOT_FOUND;
		}
		if (!sensorRule.getPropertyActuated().isValidValue(sensorRule.getActuatorSetValue())) {
			errorDTO = ErrorDTO.VALUE_IS_NOT_VALID;
		}
		if (errorDTO != null) {
			return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
		}
		return this.newSensorRule(sensorRule);
		
	}
	
	
	@RequestMapping(value="/cameraSensorRules/", method = RequestMethod.POST)
	public ResponseEntity<?> newCameraSensorRule(@RequestBody CameraSensorRuleDTO cameraSensorRuleDTO) {
		CameraSensorRule cameraSensorRule = cameraSensorRuleDTO.getSensorRule();
		if (!camerasService.existCamera(cameraSensorRule.getCameraName())) {
			ErrorDTO cameraNotFoundDTO = ErrorDTO.DEVICE_NOT_FOUND;
			return new ResponseEntity<>(cameraNotFoundDTO, cameraNotFoundDTO.getHttpStatus());
		}
		return this.newSensorRule(cameraSensorRule);
		
	}





}
