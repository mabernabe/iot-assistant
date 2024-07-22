package com.iotassistant.models.devices.transductors.propertyactuated;

interface PropertyActuated {
	
	public String getName();
	
	public String getNameWithUnit();
	
	public boolean isBinary();
	
	public String getUnit();
	
	public String getDescriptiveInformationFromValue(String value);
		
	public Integer getMaximumValue();

	public Integer getMinimumValue() ;

}
