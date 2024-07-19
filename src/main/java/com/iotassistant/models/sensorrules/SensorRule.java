package com.iotassistant.models.sensorrules;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.iotassistant.models.transductor.SensorMeasureValueEvent;
import com.iotassistant.models.AnalogThresholdOperatorEnum;
import com.iotassistant.models.notifications.NotificationHandler;
import com.iotassistant.models.notifications.NotificationTypeEnum;
import com.iotassistant.models.transductor.SensorMeasureObserver;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
@DiscriminatorColumn(name="sensorrule_type")
@Table(name="sensorRule")
public abstract class SensorRule implements SensorMeasureObserver{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	
	@OneToOne(orphanRemoval = true, cascade=CascadeType.ALL)
	private SensorMeasureThresholdSettings sensorMeasureThresholdSettings;
	
	private boolean enabled;
	
	@Enumerated(EnumType.STRING)
	private SensorRuleTriggerIntervalEnum timeBetweenTriggers;
	
	@Enumerated(EnumType.STRING)
	private SensorRuleType type;
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private NotificationTypeEnum notificationType;
	
	@Transient
	protected NotificationHandler notificationHandler;
	

	public SensorRule() {
		super();
	}

	SensorRule(SensorMeasureThresholdSettings sensorMeasureThresholdSettings, boolean enabled, SensorRuleType type, 
			SensorRuleTriggerIntervalEnum timeBetweenTriggers, NotificationTypeEnum notificationType) {
		this.sensorMeasureThresholdSettings = sensorMeasureThresholdSettings;
		this.enabled = enabled;
		this.type = type;
		this.timeBetweenTriggers = timeBetweenTriggers;
		this.notificationType = notificationType;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getSensorName() {
		return sensorMeasureThresholdSettings.getSensorName();
	}

	public PropertyMeasuredEnum getPropertyObserved() {
		return sensorMeasureThresholdSettings.getPropertyObserved();
	}


	public String getValueThresholdObserved() {
		return sensorMeasureThresholdSettings.getValueThresholdObserved();
	}

	public boolean isEnabled() {
		return enabled;
	}
	
	

	public SensorMeasureThresholdSettings getSensorMeasureThresholdSettings() {
		return sensorMeasureThresholdSettings;
	}

	public NotificationHandler getNotificationHandler() {
		return notificationHandler;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public AnalogThresholdOperatorEnum getAnalogThresholdOperator() {
		return sensorMeasureThresholdSettings.getAnalogThresholdOperator();
	}


	public SensorRuleTriggerIntervalEnum getTimeBetweenTriggers() {
		return timeBetweenTriggers;
	}

	public void setTimeBetweenTriggers(SensorRuleTriggerIntervalEnum timeBetweenTriggers) {
		this.timeBetweenTriggers = timeBetweenTriggers;
	}

	public void setType(SensorRuleType type) {
		this.type = type;
	}

	public SensorRuleType getType() {
		return type;
	}
	
	public NotificationTypeEnum getNotificationType() {
		return notificationType;
	}


	public void setNotificationType(NotificationTypeEnum notificationType) {
		this.notificationType = notificationType;
	}

	public void setNotificationHandler(NotificationHandler notificationHandler) {
		this.notificationHandler = notificationHandler;
	}
	
	
	@Override
	public void notify(SensorMeasureValueEvent sensorEvent) {
		if (this.isEnabled()) {
			this.triggerRule(sensorEvent);
		}		
	}

	protected abstract void triggerRule(SensorMeasureValueEvent sensorEvent);

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SensorRule other = (SensorRule) obj;
		if (!sensorMeasureThresholdSettings.equals(other.sensorMeasureThresholdSettings))
			return false;
		return true;
	}
	
	public abstract void accept(SensorRuleVisitor sensorRuleVisitor);

	

}
