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
import com.iotassistant.controllers.dtos.transductor.ActuatorValueDTO;
import com.iotassistant.controllers.dtos.transductor.ActuatorsDTO;
import com.iotassistant.controllers.dtos.transductor.NewMqttInterfaceActuatorDTO;
import com.iotassistant.models.transductor.Actuator;
import com.iotassistant.models.transductor.TransductorInterfaceException;
import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;
import com.iotassistant.services.ActuatorsService;
import com.iotassistant.services.TransductorsService;

@RestController
@RequestMapping("${actuators.uri}")
public class ActuatorsController {
	
	@Autowired
	private ActuatorsService actuatorsService;
	
	@Autowired
	private TransductorsService transductorsService;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ResponseEntity<?> getAllActuators() {
		ActuatorsDTO actuatorsDTO =  new ActuatorsDTO(actuatorsService.getAllActuators());
		return new ResponseEntity<>(actuatorsDTO, HttpStatus.OK);
	
	}

	
	private ResponseEntity<?> newActuator(Actuator actuator) throws TransductorInterfaceException {
		if (transductorsService.existTransductor(actuator.getName())) {
			ErrorDTO transductorExistError = ErrorDTO.DEVICE_ALREADY_EXIST;
			return new ResponseEntity<>(transductorExistError, transductorExistError.getHttpStatus());
		}
		actuatorsService.newActuator(actuator);
	    return new ResponseEntity<>(null, HttpStatus.CREATED);	
	}
	
	@RequestMapping(value="/mqttInterfaceActuators/", method = RequestMethod.POST)
	public ResponseEntity<?> newMqttInterfaceActuator(@RequestBody NewMqttInterfaceActuatorDTO newMqttInterfaceActuatorDTO) throws TransductorInterfaceException {
		return this.newActuator(newMqttInterfaceActuatorDTO.getActuator());
		
	}
	
	
	@RequestMapping(value="/{name}", method = RequestMethod.PATCH)
	public ResponseEntity<?> setActuatorValue(@PathVariable("name") String actuatorName, @RequestBody ActuatorValueDTO actuatorValueDTO) throws TransductorInterfaceException {
		ErrorDTO errorDTO = null;
		if (!actuatorsService.existActuator(actuatorName))  {
			errorDTO = ErrorDTO.DEVICE_NOT_FOUND;
			errorDTO.formatMessage("Actuator");
	    }
		PropertyActuatedEnum propertyActuated = PropertyActuatedEnum.getInstance(actuatorValueDTO.getPropertyActuated());
		if (!actuatorsService.hasActuatorProperty(actuatorName, propertyActuated)) {
			errorDTO = ErrorDTO.ACTUATOR_HAS_NOT_PROPERTY;
			errorDTO.formatMessage(propertyActuated.toString());
		}
		if (errorDTO != null) {
			return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
		}	
		actuatorsService.setActuatorValue(propertyActuated, actuatorName, actuatorValueDTO.getValue());
		return new ResponseEntity<>(null, HttpStatus.OK);
	}
	
	@RequestMapping(value="/{name}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteActuator(@PathVariable("name") String name) throws TransductorInterfaceException {
		if (!actuatorsService.existActuator(name))  {
			ErrorDTO actuatorNotFoundError = ErrorDTO.DEVICE_NOT_FOUND;
			actuatorNotFoundError.formatMessage("Actuator");
			return new ResponseEntity<>(actuatorNotFoundError, actuatorNotFoundError.getHttpStatus());
	    }
	    actuatorsService.deleteActuatorByName(name);
		return new ResponseEntity<>(null, HttpStatus.OK);

	}
	
	
	@RequestMapping(value="/{name}/watchdog", method = RequestMethod.PATCH)
	public ResponseEntity<?> enableActuatorWatchdog(@PathVariable("name") String actuatorName, @RequestBody EnableDTO enableWatchdogDTO) throws TransductorInterfaceException  {
		ErrorDTO errorDTO = null;
		if (!actuatorsService.existActuator(actuatorName))  {
			errorDTO = ErrorDTO.DEVICE_NOT_FOUND;	
	    }
		if (!actuatorsService.getActuatorByName(actuatorName).hasWatchdog())  {
			errorDTO = ErrorDTO.TRANSDUCTOR_HAS_NOT_WATCHDOG;		
	    }
		if (errorDTO != null) {
			errorDTO.formatMessage("Actuator");
			return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
		}
	    actuatorsService.enableDisableWatchdog(enableWatchdogDTO.isEnable(), actuatorName);
	    return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
