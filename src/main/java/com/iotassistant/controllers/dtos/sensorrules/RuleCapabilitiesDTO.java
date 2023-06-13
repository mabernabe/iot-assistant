package com.iotassistant.controllers.dtos.sensorrules;

import java.util.List;

public class RuleCapabilitiesDTO {
	
	private List<String> supportedSensorRulesTypes;
	
	private List<String> supportedSensorRulesTimeBetweenTriggers;

	public RuleCapabilitiesDTO(List<String> supportedSensorRulesTypes,
			List<String> supportedSensorRulesTimeBetweenTriggers) {
		super();
		this.supportedSensorRulesTypes = supportedSensorRulesTypes;
		this.supportedSensorRulesTimeBetweenTriggers = supportedSensorRulesTimeBetweenTriggers;
	}

	public List<String> getSupportedSensorRulesTypes() {
		return supportedSensorRulesTypes;
	}

	public List<String> getSupportedSensorRulesTimeBetweenTriggers() {
		return supportedSensorRulesTimeBetweenTriggers;
	}

	
	
	

}
