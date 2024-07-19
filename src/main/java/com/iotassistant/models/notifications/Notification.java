package com.iotassistant.models.notifications;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
public abstract class Notification {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
	
	private String date;
	
	

	public Notification() {
		super();
	}


	Notification(String date) {
		this();
		this.date = date;
	}


	public String getDate() {
		return date;
	}
	
	public Integer getId() {
		return id;
	}
	
	public abstract void accept(NotificationVisitor notificationVisitor);

}
