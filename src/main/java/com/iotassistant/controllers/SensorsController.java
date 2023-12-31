package com.iotassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.iotassistant.controllers.dtos.EnableDTO;
import com.iotassistant.controllers.dtos.ErrorDTO;
import com.iotassistant.controllers.dtos.transductor.NewMqttInterfaceSensorDTO;
import com.iotassistant.controllers.dtos.transductor.NewPinInterfaceSensorDTO;
import com.iotassistant.controllers.dtos.transductor.SensorDTO;
import com.iotassistant.controllers.dtos.transductor.SensorsDTO;
import com.iotassistant.models.transductor.Sensor;
import com.iotassistant.models.transductor.TransductorInterfaceException;
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
			sensorNotFoundError.formatMessage("Sensor");
			return new ResponseEntity<>(sensorNotFoundError, sensorNotFoundError.getHttpStatus());
	    }
		Sensor sensor = sensorsService.getSensorByName(name);
		return new ResponseEntity<>(new SensorDTO(sensor), HttpStatus.OK);
		
	}	
	
	@RequestMapping(value="/pinInterfaceSensors/", method = RequestMethod.POST)
	public ResponseEntity<?> newPinInterfaceSensor(@RequestBody NewPinInterfaceSensorDTO newPinInterfaceSensorDTO) throws TransductorInterfaceException {
	    return this.newSensor(newPinInterfaceSensorDTO.getSensor());
	}
	

	private ResponseEntity<?> newSensor(Sensor sensor) throws TransductorInterfaceException {
		if (transductorsService.existTransductor(sensor.getName())) {
			ErrorDTO transductorExistError = ErrorDTO.DEVICE_ALREADY_EXIST;
			return new ResponseEntity<>(transductorExistError, transductorExistError.getHttpStatus());
		}
	    sensorsService.newSensor(sensor);
	    return new ResponseEntity<>(null, HttpStatus.CREATED);	
	}

	@RequestMapping(value="/mqttInterfaceSensors/", method = RequestMethod.POST)
	public ResponseEntity<?> newMqttInterfaceSensor(@RequestBody NewMqttInterfaceSensorDTO newMqttInterfaceSensorDTO) throws TransductorInterfaceException  {
		return this.newSensor(newMqttInterfaceSensorDTO.getSensor());	
	}
	

	@RequestMapping(value="/{name}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteSensor(@PathVariable("name") String name) throws TransductorInterfaceException {
		if (!sensorsService.exist(name))  {
			ErrorDTO sensorNotFoundError = ErrorDTO.DEVICE_NOT_FOUND;
			sensorNotFoundError.formatMessage("Sensor");
			return new ResponseEntity<>(sensorNotFoundError, sensorNotFoundError.getHttpStatus());
	    }
	    sensorsService.deleteSensorByName(name);
	    return new ResponseEntity<>(null, HttpStatus.OK);	
	}
	

	@RequestMapping(value="/{name}", method = RequestMethod.PATCH)
	public ResponseEntity<?> enableSensorWatchdog(@PathVariable("name") String sensorName, @RequestBody EnableDTO enableWatchdogDTO) throws  TransductorInterfaceException  {
		ErrorDTO errorDTO = null;
		if (!sensorsService.exist(sensorName))  {
			errorDTO = ErrorDTO.DEVICE_NOT_FOUND;
	    }
		if (!sensorsService.getSensorByName(sensorName).hasWatchdog())  {
			errorDTO = ErrorDTO.TRANSDUCTOR_HAS_NOT_WATCHDOG;		
	    }
		if (errorDTO != null)  {
			errorDTO.formatMessage("Sensor");
			return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
	    }
	    sensorsService.enableDisableWatchdog(enableWatchdogDTO.isEnable(), sensorName);
	    return new ResponseEntity<>(null, HttpStatus.OK);
	}


}
