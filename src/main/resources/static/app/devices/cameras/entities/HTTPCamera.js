class HTTPCamera{
	
	constructor() {
		this.name = null;
		this.description = null;
		this.interfaceType = null;
		this.watchdogInterval = null;
		this.url = null;
	}
	
	getName () {
		return this.name;
	}
	
	getDescription () {
		return this.description;
	}
	
	getURL() {
		return this.url;
	}
	
	setName(name) {
		this.name = name;
	}
	
	setURL(url) {
		this.url = url;
	}
	
	setDescription (description) {
		this.description = description;
	}
	
	getInterfaceType() {
		return this.interfaceType;
	}
	
	setInterfaceType(interfaceType) {
		this.interfaceType = interfaceType;
	}
	
	interfaceTypeIsHTTP() {
		return this.interfaceType === "HTTP";
	}
	
	
	getWatchdogInterval () {
		return this.watchdogInterval;
	}
	
	setWatchdogInterval (watchdogInterval) {
		this.watchdogInterval = watchdogInterval;
	}
	
	isValid() {
		return this.getName() != null && this.getDescription() != null && this.isValidURL() 
		&& this.getInterfaceType() != null && this.getWatchdogInterval() != null;
	}
	
	isValidURL() {
		const urlPattern = new RegExp('^(http:\\/\\/)'+ // validate protocol
	    '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.?)+([a-z]{2,})?|'+ // validate domain name
	    '((\\d{1,3}\\.){3}\\d{1,3}))'+ // validate OR ip (v4) address
	    '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*'+ // validate port and path
	    '(\\?[;&a-z\\d%_.~+=-]*)?'+ // validate query string
	    '(\\#[-a-z\\d_]*)?$','i'); // validate fragment locator
		return this.getURL() != null && urlPattern.test(this.getURL());	
	}
	
}