package com.iotassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iotassistant.controllers.dtos.ErrorDTO;
import com.iotassistant.controllers.dtos.devices.EnableDTO;
import com.iotassistant.controllers.dtos.devices.transductors.NewMqttInterfaceSensorDTO;
import com.iotassistant.controllers.dtos.devices.transductors.SensorDTO;
import com.iotassistant.controllers.dtos.devices.transductors.SensorsDTO;
import com.iotassistant.models.devices.Sensor;
import com.iotassistant.services.SensorsService;
import com.iotassistant.services.TransductorsService;

@RestController
@RequestMapping("${sensors.uri}")
public class SensorsController {
	
	@Autowired
	private TransductorsService transductorsService;
	
	@Autowired
	private SensorsService sensorsService;
		
	@RequestMapping(value="/", method = RequestMethod.GET)
	public SensorsDTO getAllSensors() {
		return new SensorsDTO(sensorsService.getAllSensors());		
			
	}
	
	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getSensorByName(@PathVariable("name") String name) {
		if (!sensorsService.exist(name))  {
			ErrorDTO sensorNotFoundError = ErrorDTO.DEVICE_NOT_FOUND;
			return new ResponseEntity<>(sensorNotFoundError, sensorNotFoundError.getHttpStatus());
	    }
		return new ResponseEntity<>(new SensorDTO(sensorsService.getSensorByName(name)), HttpStatus.OK);
		
	}	

	@RequestMapping(value="/mqttInterfaceSensors/", method = RequestMethod.POST)
	public ResponseEntity<?> newMqttInterfaceSensor(@RequestBody NewMqttInterfaceSensorDTO newMqttInterfaceSensorDTO){
		Sensor sensor = newMqttInterfaceSensorDTO.getSensor();
		if (transductorsService.existTransductor(sensor.getName())) {
			ErrorDTO deviceExistError = ErrorDTO.DEVICE_ALREADY_EXIST;
			return new ResponseEntity<>(deviceExistError, deviceExistError.getHttpStatus());
		}
		if (sensor.getPropertiesMeasured() == null || sensor.getPropertiesMeasured().isEmpty()) {
			ErrorDTO hasNotPropertiesError = ErrorDTO.TRANSDUCTOR_HAS_NOT_PROPERTIES;
			return new ResponseEntity<>(hasNotPropertiesError, hasNotPropertiesError.getHttpStatus());
		}
	    sensorsService.newSensor(sensor);
	    return new ResponseEntity<>(null, HttpStatus.CREATED);	
	}
	

	@RequestMapping(value="/{name}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSensor(@PathVariable("name") String name) {
		if (!sensorsService.exist(name))  {
			ErrorDTO sensorNotFoundError = ErrorDTO.DEVICE_NOT_FOUND;
			return new ResponseEntity<>(sensorNotFoundError, sensorNotFoundError.getHttpStatus());
	    }
	    sensorsService.deleteSensorByName(name);
	    return new ResponseEntity<>(null, HttpStatus.OK);	
	}
	

	@RequestMapping(value="/{name}", method = RequestMethod.PATCH)
	public ResponseEntity<?> enableSensorWatchdog(@PathVariable("name") String sensorName, @RequestBody EnableDTO enableWatchdogDTO)  {
		ErrorDTO errorDTO = null;
		if (!sensorsService.exist(sensorName))  {
			errorDTO = ErrorDTO.DEVICE_NOT_FOUND;
	    }
		if (!sensorsService.getSensorByName(sensorName).hasWatchdog())  {
			errorDTO = ErrorDTO.DEVICE_HAS_NOT_WATCHDOG;		
	    }
		if (errorDTO != null)  {
			return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
	    }
	    sensorsService.enableDisableWatchdog(enableWatchdogDTO.isEnable(), sensorName);
	    return new ResponseEntity<>(null, HttpStatus.OK);
	}


}
