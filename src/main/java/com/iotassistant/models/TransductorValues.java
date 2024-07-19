package com.iotassistant.models;

import java.util.Map;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.MapKeyColumn;
import javax.persistence.MappedSuperclass;

import com.iotassistant.models.transductor.Property;

@MappedSuperclass
public abstract class TransductorValues<T extends Property> {
	
	@ElementCollection
    @CollectionTable
    @MapKeyColumn(name = "property")
    @Column(name = "value")
	private Map<T, String> values;
	
	private String date;

	public TransductorValues() {
		super();
	}

	public TransductorValues(Map<T, String> values, String date) {
		super();
		this.values = values;
		this.date = date;
	}

	public Map<T, String> getValues() {
		return values;
	}
	
	
	public String getValue(T property) {
		return this.values.get(property);
	}


	public String getDate() {
		return date;
	}
}
