package com.iotassistant.controllers.dtos;

import com.iotassistant.models.transductor.Transductor;

public abstract class TransductorDTO extends DeviceDTO{

	public TransductorDTO(Transductor transductor) {
		super(transductor);
	}

}
