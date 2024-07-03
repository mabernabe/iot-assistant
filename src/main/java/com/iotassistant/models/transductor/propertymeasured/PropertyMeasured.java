package com.iotassistant.models.transductor.propertymeasured;

public interface PropertyMeasured {
	
	public String getName();
	
	public boolean isDigital();
	
	public String getUnit();
	
	public String getDescriptiveInformationFromValue(String value);
	
	public PropertyMeasuredSeverity getSeverity(String value);
	
	public Integer getMaximumValue();

	public Integer getMinimumValue() ;

	public String getNameWithUnit();

}
