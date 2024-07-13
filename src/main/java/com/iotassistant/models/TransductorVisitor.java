package com.iotassistant.models;

import com.iotassistant.models.transductor.Actuator;
import com.iotassistant.models.transductor.Sensor;

public interface TransductorVisitor {
	
	public void visit(Sensor sensor) ;
	
	public void visit(Actuator actuator) ;

}
