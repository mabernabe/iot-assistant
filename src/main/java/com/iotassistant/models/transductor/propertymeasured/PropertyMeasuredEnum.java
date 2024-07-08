package com.iotassistant.models.transductor.propertymeasured;

import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogAirCO2PPM;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogAirCOPPM;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogAirPressurePA;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogAirQualityIAQ;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogAirTVOCPPB;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogAmbientLightLux;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogCurrentA;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogEncoder60U;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogGeneric;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogGestureId;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogHeartRatePPM;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogHumidityPercentage;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogPowerW;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogPulseOxymeterPercentage;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogSoilMoistureRH;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogTemperatureCentigrades;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogUVUVA;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogUVUVB;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogUVUVI;
import com.iotassistant.models.transductor.propertymeasured.analog.AnalogVoltageV;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalButton;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalDualButtonA;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalDualButtonB;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalFlame;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalGeneric;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalLimitSwitch;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalMotion;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalSound;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalSteam;
import com.iotassistant.models.transductor.propertymeasured.digital.DigitalTilt;


public enum PropertyMeasuredEnum implements Property{
	DIGITAL_MOTION(new DigitalMotion()),
	ANALOG_TEMPERATURE_CENTIGRADES(new AnalogTemperatureCentigrades()),
	ANALOG_HUMIDITY_PERCENTAGE(new AnalogHumidityPercentage()),
	ANALOG_AMBIENT_LIGHT_LUX(new AnalogAmbientLightLux()),
	DIGITAL_TILT(new DigitalTilt()),
	ANALOG_AIR_PRESSURE_PA(new AnalogAirPressurePA()),
	ANALOG_AIR_QUALITY_IAQ(new AnalogAirQualityIAQ()),
	ANALOG_UV_UVI(new AnalogUVUVI()),
	ANALOG_UV_UVA(new AnalogUVUVA()),
	ANALOG_UV_UVB(new AnalogUVUVB()),
	DIGITAL_FLAME(new DigitalFlame()),
	DIGITAL_STEAM(new DigitalSteam()),
	DIGITAL_SOUND(new DigitalSound()),
	DIGITAL_LIMIT_SWITCH(new DigitalLimitSwitch()),
	ANALOG_AIR_CO_PPM(new AnalogAirCOPPM()),
	ANALOG_AIR_CO2_PPM(new AnalogAirCO2PPM()),
	ANALOG_AIR_TVOC_PPB(new AnalogAirTVOCPPB()),
	ANALOG_SOIL_MOISTURE_RH(new AnalogSoilMoistureRH()),
	DIGITAL_DUAL_BUTTON_A(new DigitalDualButtonA()),
	DIGITAL_DUAL_BUTTON_B(new DigitalDualButtonB()),
	ANALOG_ENCODER_60U(new AnalogEncoder60U()),
	ANALOG_HEART_RATE_PPM(new AnalogHeartRatePPM()),
	ANALOG_PULSE_OXYMETER_PERCENTAGE(new AnalogPulseOxymeterPercentage()),
	ANALOG_GESTURE_ID(new AnalogGestureId()),
	ANALOG_VOLTAGE_V(new AnalogVoltageV()),
	ANALOG_CURRENT_A(new AnalogCurrentA()),
	ANALOG_POWER_W(new AnalogPowerW()),
	ANALOG_GENERIC_NA(new AnalogGeneric()),
	DIGITAL_GENERIC(new DigitalGeneric()),
	DIGITAL_BUTTON(new DigitalButton());
	
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
	public boolean isDigital() {
		return propertyMeasured.isDigital();
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

	public boolean isValidValue(String value) {
		if (this.isDigital()) {
			return value != null && value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false");
		} else {
			try {
				Float.parseFloat(value);
			} catch(  NullPointerException | NumberFormatException e) {
				return false;
			}		
		}
		return true;
	}


}
