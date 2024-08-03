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
import com.iotassistant.controllers.dtos.devices.GpsDTO;
import com.iotassistant.controllers.dtos.devices.GpsesDTO;
import com.iotassistant.controllers.dtos.devices.NewMqttGpsDTO;
import com.iotassistant.models.devices.Gps;
import com.iotassistant.services.GpsesService;

@RestController
@RequestMapping("${gpses.uri}")
public class GpsesController{
	
	@Autowired
	private GpsesService gpsesService;
		
	@RequestMapping(value="/", method = RequestMethod.GET)
	public GpsesDTO getAllGpses() {
		return new GpsesDTO(gpsesService.getAllGpses());		
	}
	
	@RequestMapping(value="/{name}", method = RequestMethod.GET)
	public ResponseEntity<?> getGpsByName(@PathVariable("name") String name) {
		if (!gpsesService.exist(name))  {
			ErrorDTO gpsNotFoundError = ErrorDTO.DEVICE_NOT_FOUND;
			return new ResponseEntity<>(gpsNotFoundError, gpsNotFoundError.getHttpStatus());
	    }
		return new ResponseEntity<>(new GpsDTO(gpsesService.getGpsByName(name)), HttpStatus.OK);
		
	}	

	@RequestMapping(value="/mqtt-interface-gpses/", method = RequestMethod.POST)
	public ResponseEntity<?> newMqttInterfaceGps(@RequestBody NewMqttGpsDTO newMqttGpsDTO){
		Gps gps = newMqttGpsDTO.getGps();
		if (gpsesService.existDevice(gps.getName())) {
			ErrorDTO deviceExistError = ErrorDTO.DEVICE_ALREADY_EXIST;
			return new ResponseEntity<>(deviceExistError, deviceExistError.getHttpStatus());
		}
	    gpsesService.newGps(gps);
	    return new ResponseEntity<>(null, HttpStatus.CREATED);	
	}
	

	@RequestMapping(value="/{name}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteGps(@PathVariable("name") String name) {
		if (!gpsesService.exist(name))  {
			ErrorDTO gpsNotFoundError = ErrorDTO.DEVICE_NOT_FOUND;
			return new ResponseEntity<>(gpsNotFoundError, gpsNotFoundError.getHttpStatus());
	    }
	    gpsesService.deleteGpsByName(name);
	    return new ResponseEntity<>(null, HttpStatus.OK);	
	}
	

	@RequestMapping(value="/{name}", method = RequestMethod.PATCH)
	public ResponseEntity<?> enableGpsWatchdog(@PathVariable("name") String gpsName, @RequestBody EnableDTO enableWatchdogDTO)  {
		ErrorDTO errorDTO = null;
		if (!gpsesService.exist(gpsName))  {
			errorDTO = ErrorDTO.DEVICE_NOT_FOUND;
	    }
		if (!gpsesService.getGpsByName(gpsName).hasWatchdog())  {
			errorDTO = ErrorDTO.DEVICE_HAS_NOT_WATCHDOG;		
	    }
		if (errorDTO != null)  {
			return new ResponseEntity<>(errorDTO, errorDTO.getHttpStatus());
	    }
	    gpsesService.enableDisableWatchdog(enableWatchdogDTO.isEnable(), gpsName);
	    return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
