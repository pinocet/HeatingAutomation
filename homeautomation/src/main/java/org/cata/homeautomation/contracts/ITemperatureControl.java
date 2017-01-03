package org.cata.homeautomation.contracts;

public interface ITemperatureControl extends ITemperatureNotification {

	void StartHeating(double targetTemperature);
	
	void StopHeating();
}
