package com.iotassistant.models.transductor;

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
@DiscriminatorColumn(name="actuatorInterface_type")
@Table(name="actuatorInterface")
public abstract class ActuatorInterface  {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

	

}
