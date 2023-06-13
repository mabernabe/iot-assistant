package com.iotassistant.models.transductor;

import java.util.List;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;


@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="sensorInterface_type")
@Table(name="sensorInterface")
public abstract class SensorInterface implements TransductorInterface {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	public abstract List<SensorMeasure> getMeasures() throws TransductorInterfaceException;
		
	public abstract void registerMeasureObserver(SensorMeasureObserver observer) throws TransductorInterfaceException ;
	
	public abstract void unRegisterMeasureObserver(SensorMeasureObserver observer) throws TransductorInterfaceException ;
	
	


}
