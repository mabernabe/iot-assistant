package com.iotassistant.controllers.dtos.devices.transductors;

import com.iotassistant.controllers.dtos.devices.DeviceDTO;
import com.iotassistant.models.devices.Transductor;

public abstract class TransductorDTO extends DeviceDTO{

	public TransductorDTO(Transductor transductor) {
		super(transductor);
	}

}
