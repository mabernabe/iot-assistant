package com.iotassistant.models.devices.transductors.propertymeasured;

public enum PropertyMeasuredSeverity {
	NO_SEVERITY(0),
	SEVERITY_GOOD(1),
	SEVERITY_AVERAGE(2),
	SEVERITY_LITTLE_BAD(3),
	SEVERITY_BAD(4),
	SEVERITY_WORSE(5),
	SEVERITY_VERY_BAD(6);
	
	private int severity;

	private PropertyMeasuredSeverity(int severity) {
		this.severity = severity;
	}

	public int getInt() {
		return severity;
	}

	
	

}
