package com.iotassistant.models.transductorpininterface;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import com.iotassistant.models.pininterface.PinCapability;
import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.transductor.Property;

public class TransductorPinsConfiguration {
	
	private HashMap<PinId, Property > pinsConfiguration = new HashMap<>();
	
	public TransductorPinsConfiguration(HashMap<Property, PinId> transductorPinsConfiguration) {
		for (Property property : transductorPinsConfiguration.keySet()) {
			addPin(property, transductorPinsConfiguration.get(property));
		}
	}

	public void addPin(Property property, PinId pinId) {
		assert(!pinsConfiguration.containsKey(pinId));
		assert(!pinsConfiguration.containsValue(property));
		pinsConfiguration.put(pinId, property);
	}


	public Property getProperty(PinId pinId) {
		return pinsConfiguration.get(pinId);
	}

	public List<Property> getProperties() {
		return new ArrayList<Property>(pinsConfiguration.values());
	}


	public Collection<PinId> getPinIds(PinCapability pinCapability) {
		Collection<PinId> allPins = pinsConfiguration.keySet();
		Collection<PinId> desiredPins = new ArrayList<PinId>();
		for (PinId pin : allPins) {
			if (this.getProperty(pin).isDigital() && pinCapability.equals(PinCapability.DIGITAL)) {
				desiredPins.add(pin);
			}
			else if (!this.getProperty(pin).isDigital() && pinCapability.equals(PinCapability.ANALOG)) {
				desiredPins.add(pin);
			}
			else if(pinCapability.equals(PinCapability.ANALOG_DIGITAL)) {
				desiredPins.add(pin);
			}
		}
		return desiredPins;
	}
	
	

}
