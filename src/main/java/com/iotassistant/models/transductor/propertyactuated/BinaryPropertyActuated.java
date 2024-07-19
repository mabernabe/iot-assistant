package com.iotassistant.models.transductor.propertyactuated;

abstract class BinaryPropertyActuated  implements PropertyActuated{
	
	
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
