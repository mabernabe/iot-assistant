package com.iotassistant.models.sensorrules;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.iotassistant.models.AnalogThresholdOperatorEnum;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

@Entity
@Table(name="sensorMeasureThresholdSettings")
public class SensorMeasureThresholdSettings {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	protected Integer id;
	
	@NotNull
	private String sensorName;
	
	@Enumerated(EnumType.STRING)
	protected PropertyMeasuredEnum propertyObserved;
	
	@Enumerated(EnumType.STRING)
	protected AnalogThresholdOperatorEnum analogThresholdOperator;
	
	protected String valueThresholdObserved;
	
	public SensorMeasureThresholdSettings() {};

	public SensorMeasureThresholdSettings(@NotNull String sensorName, PropertyMeasuredEnum propertyObserved,
			AnalogThresholdOperatorEnum analogThresholdOperator, String valueThresholdObserved) {
		super();
		this.sensorName = sensorName;
		this.propertyObserved = propertyObserved;
		this.analogThresholdOperator = analogThresholdOperator;
		this.valueThresholdObserved = valueThresholdObserved;
	}

	public Integer getId() {
		return id;
	}

	public String getSensorName() {
		return sensorName;
	}

	public PropertyMeasuredEnum getPropertyObserved() {
		return propertyObserved;
	}

	public AnalogThresholdOperatorEnum getAnalogThresholdOperator() {
		return analogThresholdOperator;
	}


	public String getValueThresholdObserved() {
		return valueThresholdObserved;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SensorMeasureThresholdSettings other = (SensorMeasureThresholdSettings) obj;
		if (analogThresholdOperator != other.analogThresholdOperator)
			return false;
		if (propertyObserved != other.propertyObserved)
			return false;
		if (!valueThresholdObserved.equals(other.getValueThresholdObserved()))
			return false;
		if (sensorName == null) {
			if (other.sensorName != null)
				return false;
		} else if (!sensorName.equals(other.sensorName))
			return false;
		return true;
	}
	
	

}
