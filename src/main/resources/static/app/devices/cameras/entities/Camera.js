class Camera extends Device{
	
	constructor(name, description, active, watchdogInterval, watchdogEnabled, urlPicture) {
		super(name, description, active, watchdogInterval, watchdogEnabled);
		this.urlPicture = urlPicture + '?decache=' + Math.random();
	}
		
	getPictureURL() {
		return this.urlPicture;
	}
	
}