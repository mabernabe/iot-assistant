package com.iotassistant.models.transductor;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.iotassistant.models.transductor.propertymeasured.PropertyMeasuredEnum;

@Entity
@DiscriminatorValue("Sensor")
@Table(name="sensor")
public class Sensor extends Transductor {

	@OneToOne(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	protected SensorInterface sensorInterface;

	public Sensor() {
		super();
	}

	public Sensor(String name, String description, SensorInterface sensorInterface, WatchdogInterval watchdogInterval) {
		super(name, description, watchdogInterval);
		this.sensorInterface = sensorInterface;
	}

	public List<SensorMeasure> getMeasures() throws TransductorInterfaceException {
		return sensorInterface.getMeasures();
	}


	public void registerMeasureObserver(SensorMeasureObserver observer) throws TransductorInterfaceException  {
			sensorInterface.registerMeasureObserver(observer);
	}



	public void unRegisterMeasureObserver(SensorMeasureObserver observer) throws TransductorInterfaceException  {
			sensorInterface.unRegisterMeasureObserver(observer);		
	}

	public SensorInterface getInterface() {
		return this.sensorInterface;
	}

	public void setInterface(SensorInterface sensorInterface) {
		this.sensorInterface = sensorInterface;
		
	}

	@Override
	public List<Property> getProperties() {
		return sensorInterface.getProperties();
	}

	public void setUpInterface() throws TransductorInterfaceException {
		sensorInterface.setUp();		
	}
	
	public void setDownInterface() throws TransductorInterfaceException {
		sensorInterface.setDown();		
	}

	public SensorMeasure getMeasure(PropertyMeasuredEnum propertyObserved) throws TransductorInterfaceException {
		assert(getProperties().contains(propertyObserved));
		SensorMeasure measureObserved = null;
		for(SensorMeasure measure: this.getMeasures()) {
			if (measure.getPropertyMeasured().equals(propertyObserved)) {
				measureObserved = measure;
			}
		}
		return measureObserved;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<TransductorValue> getTransductorValues() throws TransductorInterfaceException {
		return (List<TransductorValue>) (List<? extends TransductorValue>) this.getMeasures();
	}


}
