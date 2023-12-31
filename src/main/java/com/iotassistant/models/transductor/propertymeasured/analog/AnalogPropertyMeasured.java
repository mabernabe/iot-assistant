package com.iotassistant.models.transductor.propertymeasured.analog;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasured;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredSeverity;

public abstract class AnalogPropertyMeasured implements PropertyMeasured{


	@Override
	public boolean isDigital() {
		return false;
	}

	@Override
	public abstract String getUnit();

	@Override
	public abstract String getDescriptiveInformationFromValue(String value);

	@Override
	public abstract PropertyMeasuredSeverity getSeverity(String value);

	@Override
	public abstract Integer getMaximumValue();

	@Override
	public abstract Integer getMinimumValue();
	
	@Override
	public String toStringWithUnit() {
		String string = this.getName();
		String unit = this.getUnit();
		return string.concat(" ").concat(unit);
	}

}
