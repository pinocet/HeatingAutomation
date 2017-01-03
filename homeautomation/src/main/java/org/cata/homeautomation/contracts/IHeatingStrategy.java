package org.cata.homeautomation.contracts;

import org.cata.homeautomation.dataobjects.AbstractHeatingParameters;

public interface IHeatingStrategy {
	
	void OnActivated(AbstractHeatingParameters heatingParams);
	
	void OnDeactivated();
	
	void OnCurrentTemperatureChanged(double temp);
	
	String GetName();

}
