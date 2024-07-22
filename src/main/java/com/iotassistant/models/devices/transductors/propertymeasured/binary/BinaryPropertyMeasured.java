package com.iotassistant.models.devices.transductors.propertymeasured.binary;

import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasured;
import com.iotassistant.models.devices.transductors.propertymeasured.PropertyMeasuredSeverity;

abstract class BinaryPropertyMeasured implements PropertyMeasured{
	
	
	@Override
	public boolean isBinary() {
		return true;
	}
	
	@Override
	public String getUnit() {
		return null;
	}
	
	@Override
	public String getDescriptiveInformationFromValue(String value) {
		return null;
	}
	
	@Override
	public PropertyMeasuredSeverity getSeverity(String value) {
		return null;
	}
	
	@Override
	public Integer getMaximumValue() {
		return null;
	}

	@Override
	public Integer getMinimumValue() {
		return null;
	}
	

	@Override
	public String getNameWithUnit() {
		return this.getName();
	}
	
}
