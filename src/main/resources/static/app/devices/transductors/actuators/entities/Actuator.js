

class Actuator extends Transductor{
	
	constructor(name, description, active, values, propertiesActuated, watchdogInterval, watchdogEnabled) {
		super(name, description, active, propertiesActuated, watchdogInterval, watchdogEnabled);
		this.values = values;
	}

	getValuesDate() {
		return this.values.getDate();
	}
	
	getValue(propertyActuated) {
		return this.values.getValue(propertyActuated);
	}
	
}