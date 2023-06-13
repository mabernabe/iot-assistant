package com.iotassistant.models;

import com.iotassistant.models.transductor.Transductor;

public interface DeviceVisitor {

	public void visit(Transductor transductor);
	
	public void visit(Camera camera);

}
