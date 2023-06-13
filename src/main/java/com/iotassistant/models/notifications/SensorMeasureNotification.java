package com.iotassistant.models.notifications;

import com.iotassistant.models.AnalogThresholdOperatorEnum;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

public interface SensorMeasureNotification {
	
	public String getSensorName();
	
	public PropertyMeasuredEnum getPropertyObserved();
	
	public String getSensorValue();
	
	public AnalogThresholdOperatorEnum getValueThresholdOperator();
	
	public String getValueThresholdObserved();
	
	public String getDate();
	

}
