class Camera extends Device{
	
	constructor(name, description, watchdogInterval, watchdogEnabled, urlPicture) {
		super(name, description, watchdogInterval, watchdogEnabled);
		this.urlPicture = urlPicture + '?decache=' + Math.random();
	}
		
	getPictureURL() {
		return this.urlPicture;
	}
	
}