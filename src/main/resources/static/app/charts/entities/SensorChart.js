class SensorChart {
	
	constructor(id, sensorName, sensorProperty, type, totalInterval, sampleInterval, sensorChartSamples) {
		if(!arguments.length) {
			this.id;
			this.sensorName;
			this.sensorProperty;
			this.type;	
			this.totalInterval;
			this.sampleInterval;
			this.sensorChartSamples = [];
		} else {
			this.id = id;
			this.sensorName = sensorName
			this.sensorProperty = sensorProperty;
			this.type = type;
			this.totalInterval = totalInterval;
			this.sampleInterval = sampleInterval;
			this.sensorChartSamples = sensorChartSamples;
		}
	}

	getSensorName() {
		return this.sensorName;
	}
	
	
	setSensorName(sensorName) {
		this.sensorName = sensorName;
	}
	
	getSensorProperty() {
		return this.sensorProperty ;
	}
	
	setSensorProperty(sensorProperty) {
		this.sensorProperty = sensorProperty;
	}
	
	getSensorPropertyName() {
		if (typeof this.sensorProperty === 'undefined') {
			return this.sensorProperty;
		}
		return this.sensorProperty.getName() ;
	}
	
	getSensorPropertyUnit() {
		return this.sensorProperty.getUnit() ;
	}
	
	setType(type) {
		this.type = type;
	}
	
	getType() {
		return this.type;
	}
	
	
	isValid() {
		var allValuesAreSet = false;
		if (typeof this.sensorName !== 'undefined' && typeof this.sensorProperty !== 'undefined' &&
		    typeof this.type !== 'undefined'  && typeof this.totalInterval !== 'undefined' &&
		    typeof this.sampleInterval !== 'undefined') {
			allValuesAreSet = true;
		}		
		return allValuesAreSet;
	}
	
	
	setId(id) {
		this.id = id;
	}
	
	getId() {
		return this.id;
	}
	
	getTotalInterval() {
		return this.totalInterval;
	}
	
	setTotalInterval(totalInterval) {
		this.totalInterval = totalInterval;
	}
	
	getSampleInterval() {
		return this.sampleInterval;
	}
	
	setSampleInterval(sampleInterval) {
		this.sampleInterval = sampleInterval;
	}
	
	getSensorChartSamples() {
		return this.sensorChartSamples;
	}
	
	setSensorChartSamples(sensorChartSamples) {
		this.sensorChartSamples = sensorChartSamples;
	}
	
	getXData() {
		var xData = [];
		for (var i = 0; i < this.sensorChartSamples.length; i++) {
    		xData.push(this.sensorChartSamples[i].getDate());
		}
		return xData;
	}
	
	getYData() {
		var yData = [];
		for (var i = 0; i < this.sensorChartSamples.length; i++) {
			var value = this.sensorChartSamples[i].getValue();
			if (this.sensorProperty.isDigital()) {
				value = TransductorValue.getDigitalIntValue(value);
			}
    		yData.push(value);
		}
		return yData;
	}
	
	
	
}
