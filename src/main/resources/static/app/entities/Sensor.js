

class Sensor extends Transductor{
	
	constructor(measures, name, description, propertiesMeasured, watchdogInterval, watchdogEnabled) {
		super(name, description, propertiesMeasured, watchdogInterval, watchdogEnabled);
		this.measures = measures;
	}
	
	
	getMeasuresDate() {
		return this.measures[0].getDate();
	}
	
	
	getMeasure(propertyMeasuredName) {
		var measure = null;
		var measures = this.measures;
		for (var i = 0; i < measures.length; i++) {
    		if(measures[i].getProperty() === propertyMeasuredName) {
    			measure = measures[i];
    		}
		}
		return measure;
	}
	
	existMeasure(propertyMeasuredName) {
	    return this.getMeasure(propertyMeasuredName) !== null;
	}
	
	
	getValueDescription(propertyMeasuredName) {
		var valueDescription = null;
		var measures = this.measures;
		for (var i = 0; i < measures.length; i++) {
    		if(measures[i].getProperty() === propertyMeasuredName) {
    			valueDescription = measures[i].getValueDescription();
    		}
		}
		return valueDescription;
	}
	
	existValueDescription(propertyMeasuredName) {
	    return this.getValueDescription(propertyMeasuredName) !== null;
	}
	
}

