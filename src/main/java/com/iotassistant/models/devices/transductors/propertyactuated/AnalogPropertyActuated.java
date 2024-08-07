package com.iotassistant.models.devices.transductors.propertyactuated;

abstract class AnalogPropertyActuated implements PropertyActuated{

	@Override
	public abstract String getName();

	@Override
	public boolean isBinary() {
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
	public String getNameWithUnit() {
		String name = this.getName();
		String unit = this.getUnit();
		return name.concat(" ").concat(unit);
	}

}
