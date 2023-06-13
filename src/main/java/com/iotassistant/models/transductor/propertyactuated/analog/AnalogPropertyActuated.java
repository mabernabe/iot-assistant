package com.iotassistant.models.transductor.propertyactuated.analog;

import com.iotassistant.models.transductor.propertyactuated.PropertyActuated;

public abstract class AnalogPropertyActuated implements PropertyActuated{

	@Override
	public abstract String getName();

	@Override
	public boolean isDigital() {
		return false;
	}

	@Override
	public abstract String getUnit();

	@Override
	public abstract String getDescriptiveInformationFromValue(String value);

	@Override
	public abstract Integer getMaximumValue();

	@Override
	public abstract Integer getMinimumValue();

	@Override
	public String toStringWithUnit() {
		String stringWithUnit = this.getName();
		String unit = this.getUnit();
		if (unit != null) {
			stringWithUnit = stringWithUnit.concat(" ").concat(unit);
		}
		return stringWithUnit;
	}

}
