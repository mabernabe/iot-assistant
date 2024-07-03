class StationMqttInterfaceCapabilities extends TransductorInterfaceCapabilities{

	constructor(interfaceName, isAvailable, broker) {
		var details = 'Broker is ' + broker;
		super(interfaceName, isAvailable, details);
	}
	


}