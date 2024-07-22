package com.iotassistant.models.devices.transductors.propertymeasured;

public interface PropertyMeasured {
	
	public String getName();
	
	public boolean isBinary();
	
	public String getUnit();
	
	public String getDescriptiveInformationFromValue(String value);
	
	public PropertyMeasuredSeverity getSeverity(String value);
	
	public Integer getMaximumValue();

	public Integer getMinimumValue() ;

	public String getNameWithUnit();

}
