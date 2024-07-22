package com.iotassistant.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.iotassistant.controllers.dtos.ErrorDTO;
import com.iotassistant.controllers.dtos.notifications.NotificationsDTO;
import com.iotassistant.services.NotificationsService;

@RestController
@RequestMapping("${notifications.uri}")
public class NotificationsController {
	
	public static final String CAMERA_SENSOR_RULE_NOTIFICATION_ATTACHMENT = "/attachment";
	
	private @Autowired
	NotificationsService notificationsService;
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public NotificationsDTO getAllNotifications() {
		final String baseUrlNotificationsAttachment = ServletUriComponentsBuilder.fromCurrentRequestUri().build().toUriString();
		return new NotificationsDTO(notificationsService.getAllNotifications(), baseUrlNotificationsAttachment);		
			
	}
	
	@RequestMapping(value="/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<?> deleteNotificationById(@PathVariable("id") int id) {
	    if (notificationsService.getNotificationById(id) == null) {
	    	ErrorDTO notificationNotFoundErrorDTO = ErrorDTO.DEVICE_NOT_FOUND;
	    	return new ResponseEntity<>(notificationNotFoundErrorDTO, notificationNotFoundErrorDTO.getHttpStatus());
	    }
	    notificationsService.deleteNotificationById(id);
	    return new ResponseEntity<>(null, HttpStatus.OK);			
	}
	
	@RequestMapping(method = RequestMethod.DELETE)
	public void deleteAllNotifications() {
	    notificationsService.deleteAllNotifications();			
	}
	
	@RequestMapping(value="/{id}" + CAMERA_SENSOR_RULE_NOTIFICATION_ATTACHMENT, method = RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<?> getCameraSensorRuleNotificationAttachment(@PathVariable("id") int id) {
		if (notificationsService.getNotificationById(id) == null) {
	    	ErrorDTO notificationNotFoundErrorDTO = ErrorDTO.DEVICE_NOT_FOUND;
	    	return new ResponseEntity<>(notificationNotFoundErrorDTO, notificationNotFoundErrorDTO.getHttpStatus());
	    }
		return new ResponseEntity<>(notificationsService.getCameraSensorRuleAttachment(id), HttpStatus.OK);		
	}

}
