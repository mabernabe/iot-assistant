class StationPinInterfaceCapabilities extends TransductorInterfaceCapabilities{

	constructor(interfaceName, isAvailable, availableDigitalPinIds, availableAnalogPinIds, platformPinInterfaceName) {
		var details = availableDigitalPinIds.length.toString() + ' available digital pins, ' + availableAnalogPinIds.length.toString() + ' available analog pins';
		super(interfaceName, isAvailable, details);
		this.availableDigitalPinIds = availableDigitalPinIds;
		this.availableAnalogPinIds = availableAnalogPinIds;
		this.platformPinInterfaceName = platformPinInterfaceName;
	}
	

	getAvailableDigitalPinIds() {
		return this.availableDigitalPinIds;
	}


	getAvailableAnalogPinIds() {
		return this.availableAnalogPinIds;
	}

	isPinAvailable(propertyName) {
		return this.getAvailablePinIds(propertyName).length > 0;
	}

	
	getAvailablePinIds(digitals) {
		if (digitals) {
			return this.availableDigitalPinIds;
		}
		else {
			return this.availableAnalogPinIds;
		}
	}

	getPlatformPinInterfaceName() {
		return this.platformPinInterfaceName;
	}

}