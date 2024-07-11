class Notification {
	
	
	constructor(id, date) {
		this.id = id;
		this.date = date;
	}

	
	getId() {
		return this.id;
	}
	
	getDate() {
		return this.date;
	}
	
	hasAttachmnet() {
		return false;
	}
	
	
}