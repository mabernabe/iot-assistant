class GpsPosition {
	
	constructor(longitude, latitude, date) {
		this.longitude = longitude;
		this.latitude = latitude;
		this.date = date;
	}
		
	getLongitude() {
		return this.longitude;
	}
	
	getLatitude() {
		return this.latitude;
	}
	
	getDate() {
		return this.date;
	}
	
	
}