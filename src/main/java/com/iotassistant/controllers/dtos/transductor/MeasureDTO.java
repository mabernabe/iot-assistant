package com.iotassistant.controllers.dtos.transductor;

import com.iotassistant.models.transductor.SensorMeasure;

public class MeasureDTO extends TransductorValueDTO{

	private int severity;
	

	public MeasureDTO(SensorMeasure measure) {
		value = measure.getValue();
		unit = measure.getUnit();
		property = measure.getPropertyMeasured().toStringWithUnit();
		this.date = measure.getDate();
		this.severity = measure.getPropertyMeasured().getSeverity(value);
		this.valueDescription = measure.getPropertyMeasured().getDescriptiveInformationFromValue(value);
	}

	public String getPropertyMeasured() {
		return property;
	}

	public String getDate() {
		return date;
	}

	public int getSeverity() {
		return severity;
	}

	public String getValueDescription() {
		return valueDescription;
	}

	
	
	

}
