package com.iotassistant.models.devices;

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
@DiscriminatorColumn(name="gpsInterface_type")
@Table(name="gpsInterface")
public abstract class GpsInterface extends DeviceInterface{
	
	@Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private int id;



}
