

class Actuator extends Transductor{
	
	constructor(name, description, propertiesActuated, values, watchdogInterval, watchdogEnabled) {
		super(name, description, propertiesActuated, watchdogInterval, watchdogEnabled);
		this.values = values;
	}

	getValues() {
		return this.values;
	}
	
	
	existValue(propertyActuatedName) {
	    return this.getValue(propertyActuatedName) !== null;
	}
	
	getValuesDate() {
		return this.values[0].getDate();
	}
	
	
	getValue(propertyActuatedName) {
		var value = null;
		var values = this.values;
		for (var i = 0; i < values.length; i++) {
    		if(values[i].getProperty() === propertyActuatedName) {
    			value = values[i];
    		}
		}
		return value;
	}
	
}