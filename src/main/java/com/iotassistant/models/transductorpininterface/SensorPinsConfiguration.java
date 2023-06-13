package com.iotassistant.models.transductorpininterface;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.Transient;

import com.iotassistant.models.pininterface.PinCapability;
import com.iotassistant.models.pininterface.PinId;
import com.iotassistant.models.transductor.Property;
import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

@Entity
public class SensorPinsConfiguration{
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "pinid_property_mapping", 
      joinColumns = {@JoinColumn(name = "sensorPinConfiguration", referencedColumnName = "id")})
	@MapKeyColumn(name = "pinId")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "property")
	@Enumerated(EnumType.STRING)
	private Map<PinId, PropertyMeasuredEnum > pinsConfiguration = new HashMap<>();
	
	@Transient
	TransductorPinsConfiguration transductorPinsConfiguration;
	
	

	public SensorPinsConfiguration() {
		super();
	}

	public SensorPinsConfiguration(HashMap<PropertyMeasuredEnum, PinId> sensorPinsConfiguration) {
		HashMap<Property, PinId> transductorPinsConfiguration = new HashMap<>();
		for (PropertyMeasuredEnum propertyMeasured : sensorPinsConfiguration.keySet()) {
			transductorPinsConfiguration.put(propertyMeasured, sensorPinsConfiguration.get(propertyMeasured));
			this.pinsConfiguration.put(sensorPinsConfiguration.get(propertyMeasured), propertyMeasured);
		}	
		this.transductorPinsConfiguration = new TransductorPinsConfiguration(transductorPinsConfiguration);
	}

	public Collection<PinId> getPins(PinCapability pinCapability) {
		return transductorPinsConfiguration.getPinIds(pinCapability);
	}

	public PropertyMeasuredEnum getPropertyMeasured(PinId pinId) {
		return (PropertyMeasuredEnum) transductorPinsConfiguration.getProperty(pinId);
	}

	public List<Property> getProperties() {
		return transductorPinsConfiguration.getProperties();
	}
	
	@PostLoad
	@PostPersist
	private void setTransductorPinsConfiguration() {
		HashMap<Property, PinId> transductorPinsConfiguration = new HashMap<>();
		for (PinId pinId : this.pinsConfiguration.keySet()) {
			transductorPinsConfiguration.put(this.pinsConfiguration.get(pinId), pinId);
		}	
		this.transductorPinsConfiguration = new TransductorPinsConfiguration(transductorPinsConfiguration);
	}

}
