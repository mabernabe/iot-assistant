package com.iotassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.iotassistant.controllers.dtos.ErrorDTO;
import com.iotassistant.controllers.dtos.devices.CamerasDTO;
import com.iotassistant.controllers.dtos.devices.HttpCameraDTO;
import com.iotassistant.models.devices.Camera;
import com.iotassistant.models.devices.CameraInterfaceException;
import com.iotassistant.services.CamerasService;

@RestController
@RequestMapping("${cameras.uri}")
public class CamerasController { 
	
	public static final String CAMERA_PICTURE_URL = "/picture";
	
	@Autowired
	private CamerasService camerasService;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public CamerasDTO getAllCameras() {
		final String baseUrlCameraPicture = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString();
		return new CamerasDTO(camerasService.getAllCameras(), baseUrlCameraPicture);	
	}
	
	@RequestMapping(value="/http-interface-cameras/", method = RequestMethod.POST)
	public ResponseEntity<?> newHTTPInterfaceCamera(@RequestBody HttpCameraDTO httpCameraDTO)  { 
		Camera camera = httpCameraDTO.getCamera();
		if (camerasService.existDevice(camera.getName())) {
			ErrorDTO deviceExistError = ErrorDTO.DEVICE_ALREADY_EXIST;
			return new ResponseEntity<>(deviceExistError, deviceExistError.getHttpStatus());
		}
		camerasService.newCamera(camera);
		return new ResponseEntity<>(null, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(value="/{name}" + CAMERA_PICTURE_URL, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> getCameraPicture(@PathVariable("name") String name) throws CameraInterfaceException {
		if (!camerasService.existCamera(name))  {
			ErrorDTO cameraNotFoundError = ErrorDTO.DEVICE_NOT_FOUND;
			return new ResponseEntity<>(cameraNotFoundError, cameraNotFoundError.getHttpStatus());
	    }
		return new ResponseEntity<>(camerasService.getPicture(name), HttpStatus.OK);	
			
	}
	
	@RequestMapping(value="/{name}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteCamera(@PathVariable("name") String name)  { 
		if (!camerasService.existCamera(name))  {
			ErrorDTO cameraNotFoundError = ErrorDTO.DEVICE_NOT_FOUND;
			return new ResponseEntity<>(cameraNotFoundError, cameraNotFoundError.getHttpStatus());
	    }
		camerasService.deleteCamera(name);
		return new ResponseEntity<>(null, HttpStatus.OK);
			
	}

}
