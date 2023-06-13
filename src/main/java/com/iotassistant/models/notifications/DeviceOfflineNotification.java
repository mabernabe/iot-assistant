package com.iotassistant.models.notifications;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.iotassistant.models.Device;

@Entity
public class DeviceOfflineNotification extends Notification{
	
	@NotNull
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	protected Device device;

	public DeviceOfflineNotification() {
		super();
	}

	public DeviceOfflineNotification(Device device, String date) {
		super(date);
		this.device = device;
	}

	public String getDeviceName() {
		return device.getName();
	}
	
	@Override
	public void accept(NotificationVisitor notificationVisitor) {
		notificationVisitor.visit(this);		
	}

}
