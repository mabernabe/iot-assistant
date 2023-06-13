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

import com.iotassistant.models.transductor.propertyactuated.PropertyActuatedEnum;

@Entity
@Inheritance(strategy=InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn(name="actuatorInterface_type")
@Table(name="actuatorInterface")
public abstract class ActuatorInterface implements TransductorInterface {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	public abstract void setValue(PropertyActuatedEnum propertyActuated, String value) throws TransductorInterfaceException;
	
	public abstract List<ActuatorValue> getValues()  throws TransductorInterfaceException;
	
	

}
