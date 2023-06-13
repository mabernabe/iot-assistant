class TransductorOfflineNotification extends Notification{
		
	constructor(id, date, transductorName) {
		super(id, date);
		this.transductorName = transductorName;
	}
	
	toString() {
		var transductorName = this.transductorName;
		var text = "Component " + transductorName + " went offline" + ". Date: " + this.date;
		return text;
	}
	
	
}