class TransductorPinInterface {

	constructor(pinsConfiguration) {
		this.pinsConfiguration = pinsConfiguration;
	}

	getPinsConfiguration() {
		return this.pinsConfiguration;
	}

	isValid(sensorProperties) {
		var dataIsValid = false;
		var selectedPinIds = Array.from(this.pinsConfiguration.values());
		var allPropertiesHasPin = selectedPinIds.length === sensorProperties.length;
		var areDuplicatedPinIds = new Set(selectedPinIds).size != selectedPinIds.length;
		if (allPropertiesHasPin && !areDuplicatedPinIds) {
			dataIsValid = true;
		}
		return dataIsValid;
	}
}