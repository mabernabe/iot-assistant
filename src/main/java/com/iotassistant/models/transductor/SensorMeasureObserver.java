package com.iotassistant.models.transductor;

import com.iotassistant.models.AnalogThresholdOperatorEnum;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

public interface SensorMeasureObserver {
	
	
	public abstract  void notify(SensorMeasureValueEvent sensorEvent);

	public String getSensorName();

	public PropertyMeasuredEnum getPropertyObserved();

	public String getValueThresholdObserved();

	public AnalogThresholdOperatorEnum getAnalogThresholdOperator();


}
