package com.iotassistant.models;

import java.util.ArrayList;
import java.util.List;

public enum SensorChartTypeEnum {
	LINE_POINTS("Line Chart");

	private String string;
	
	private SensorChartTypeEnum(String string) {
		this.string = string;
	}
	
	@Override
	public String toString() {
		return string;
	}

	public static List<String >getAvailableChartTypes() {
		List<String> availableTypes = new ArrayList<String>();
		for (SensorChartTypeEnum chartType : SensorChartTypeEnum.values()) {
			availableTypes.add(chartType.toString());
		}
		return availableTypes;
	}

	public static SensorChartTypeEnum getInstance(String string) {
		SensorChartTypeEnum type= null;
		for (SensorChartTypeEnum typeInstance : SensorChartTypeEnum.values()) { 
			if (typeInstance.toString().equals(string)) {
				type = typeInstance;
			}; 
		}
		return type;
	}

}

