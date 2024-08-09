package com.iotassistant.models.devices.transductors.propertymeasured;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.iotassistant.models.devices.transductors.Property;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogAirCO2PPM;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogAirCOPPM;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogAirPressurePA;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogAirQualityIAQ;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogAirTVOCPPB;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogAmbientLightLux;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogCurrentA;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogEncoder60U;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogGeneric;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogGestureId;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogHeartRatePPM;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogHumidityPercentage;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogPowerW;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogPulseOxymeterPercentage;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogSoilMoistureRH;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogTemperatureCentigrades;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogUVUVA;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogUVUVB;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogUVUVI;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogVoiceId;
import com.iotassistant.models.devices.transductors.propertymeasured.analog.AnalogVoltageV;
import com.iotassistant.models.devices.transductors.propertymeasured.binary.BinaryButton;
import com.iotassistant.models.devices.transductors.propertymeasured.binary.BinaryDualButtonA;
import com.iotassistant.models.devices.transductors.propertymeasured.binary.BinaryDualButtonB;
import com.iotassistant.models.devices.transductors.propertymeasured.binary.BinaryFlame;
import com.iotassistant.models.devices.transductors.propertymeasured.binary.BinaryGeneric;
import com.iotassistant.models.devices.transductors.propertymeasured.binary.BinaryLimitSwitch;
import com.iotassistant.models.devices.transductors.propertymeasured.binary.BinaryMotion;
import com.iotassistant.models.devices.transductors.propertymeasured.binary.BinarySound;
import com.iotassistant.models.devices.transductors.propertymeasured.binary.BinarySteam;
import com.iotassistant.models.devices.transductors.propertymeasured.binary.BinaryTilt;


public enum PropertyMeasuredEnum implements Property{
	BINARY_MOTION(new BinaryMotion()),
	TEMPERATURE_CENTIGRADES(new AnalogTemperatureCentigrades()),
	HUMIDITY_PERCENTAGE(new AnalogHumidityPercentage()),
	AMBIENT_LIGHT_LUX(new AnalogAmbientLightLux()),
	BINARY_TILT(new BinaryTilt()),
	AIR_PRESSURE_PA(new AnalogAirPressurePA()),
	AIR_QUALITY_IAQ(new AnalogAirQualityIAQ()),
	UV_UVI(new AnalogUVUVI()),
	UV_UVA(new AnalogUVUVA()),
	UV_UVB(new AnalogUVUVB()),
	BINARY_FLAME(new BinaryFlame()),
	BINARY_STEAM(new BinarySteam()),
	BINARY_SOUND(new BinarySound()),
	BINARY_LIMIT_SWITCH(new BinaryLimitSwitch()),
	AIR_CO_PPM(new AnalogAirCOPPM()),
	AIR_CO2_PPM(new AnalogAirCO2PPM()),
	AIR_TVOC_PPB(new AnalogAirTVOCPPB()),
	SOIL_MOISTURE_RH(new AnalogSoilMoistureRH()),
	BINARY_DUAL_BUTTON_A(new BinaryDualButtonA()),
	BINARY_DUAL_BUTTON_B(new BinaryDualButtonB()),
	ENCODER_60U(new AnalogEncoder60U()),
	HEART_RATE_PPM(new AnalogHeartRatePPM()),
	PULSE_OXYMETER_PERCENTAGE(new AnalogPulseOxymeterPercentage()),
	GESTURE_ID(new AnalogGestureId()),
	VOICE_ID(new AnalogVoiceId()),
	VOLTAGE_V(new AnalogVoltageV()),
	CURRENT_A(new AnalogCurrentA()),
	POWER_W(new AnalogPowerW()),
	GENERIC_NA(new AnalogGeneric()),
	BINARY_GENERIC(new BinaryGeneric()),
	BINARY_BUTTON(new BinaryButton());
	
	public static final List<PropertyMeasuredEnum> ALL_INSTANCES = Arrays.asList(PropertyMeasuredEnum.values()); 
	
	private PropertyMeasured propertyMeasured;

	PropertyMeasuredEnum(PropertyMeasured propertyMeasured) {
		this.propertyMeasured = propertyMeasured;
	}

	@Override
	public String getName() {
		return propertyMeasured.getName();
	}
	
	@Override
	public String getNameWithUnit() {
		return propertyMeasured.getNameWithUnit();
	}

	@Override
	public boolean isBinary() {
		return propertyMeasured.isBinary();
	}


	public String getDescriptionFromValue(String value) {
		return propertyMeasured.getDescriptiveInformationFromValue(value);
	}


	public int getSeverity(String value) {
		PropertyMeasuredSeverity severity = PropertyMeasuredSeverity.NO_SEVERITY;
		if (propertyMeasured.getSeverity(value) != null) {
			severity = propertyMeasured.getSeverity(value);
		}
		return severity.getInt();
	}
	

	public String getUnit() {
		return propertyMeasured.getUnit();
	}


	@JsonCreator
	public static PropertyMeasuredEnum getInstance(String string) {
		for (PropertyMeasuredEnum propertyMeasured : PropertyMeasuredEnum.values()) { 
			if (propertyMeasured.toString().equals(string) || propertyMeasured.getNameWithUnit()!= null && propertyMeasured.getNameWithUnit().equals(string)) {
				return propertyMeasured;
			}; 		
		}
		return null;
	}

	public Integer getMaximumValue() {
		return propertyMeasured.getMaximumValue();
	}

	public Integer getMinimumValue() {
		return propertyMeasured.getMinimumValue();
	}




}
