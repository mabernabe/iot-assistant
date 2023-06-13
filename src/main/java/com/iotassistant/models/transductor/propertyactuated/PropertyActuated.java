package com.iotassistant.models.transductor.propertyactuated;

public interface PropertyActuated {
	
	public String getName();
	
	public boolean isDigital();
	
	public String getUnit();
	
	public String getDescriptiveInformationFromValue(String value);
		
	public Integer getMaximumValue();

	public Integer getMinimumValue() ;

	public String toStringWithUnit();

}
