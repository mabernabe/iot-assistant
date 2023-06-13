class StationChartCapabilities {

	constructor(supportedChartTypes, supportedChartIntervals, supportedSampleIntervals) {
		this.supportedChartTypes = supportedChartTypes;
		this.supportedChartIntervals = supportedChartIntervals;
		this.supportedSampleIntervals = supportedSampleIntervals;
	}

	getSupportedChartTypes() {
		return this.supportedChartTypes;
	}
	
	getSupportedChartIntervals() {
		return this.supportedChartIntervals;
	}
	
	getSupportedSampleIntervals() {
		return this.supportedSampleIntervals;
	}
	
}