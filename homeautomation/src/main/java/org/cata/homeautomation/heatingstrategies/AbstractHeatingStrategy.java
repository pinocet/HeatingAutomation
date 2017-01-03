package org.cata.homeautomation.heatingstrategies;

import org.cata.homeautomation.contracts.IHeatingStrategy;
import org.cata.homeautomation.contracts.ITemperatureControl;

public abstract class AbstractHeatingStrategy implements IHeatingStrategy {

	protected ITemperatureControl _tempControl;
	
	public AbstractHeatingStrategy(ITemperatureControl tempControl) {
		_tempControl = tempControl;
	}
	
	public abstract String GetName();

}
