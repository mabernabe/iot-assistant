package com.iotassistant.models;

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
@DiscriminatorColumn(name="cameraInterface_type")
@Table(name="cameraInterface")
public abstract class CameraInterface {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
	
	public abstract void accept(CameraInterfaceVisitor cameraInterfaceVisitor) throws CameraInterfaceException;
	
}
