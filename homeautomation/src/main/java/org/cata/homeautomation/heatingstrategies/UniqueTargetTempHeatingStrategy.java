package org.cata.homeautomation.heatingstrategies;

import org.cata.homeautomation.contracts.IHeatingStrategy;
import org.cata.homeautomation.contracts.ITemperatureControl;
import org.cata.homeautomation.dataobjects.AbstractHeatingParameters;
import org.cata.homeautomation.dataobjects.UniqueTargetTempHeatingParameters;

public class UniqueTargetTempHeatingStrategy extends AbstractHeatingStrategy implements IHeatingStrategy {

	public UniqueTargetTempHeatingStrategy(ITemperatureControl tempControl)
	{
		super(tempControl);
	}
	
	@Override
	public String GetName() {
		return "UniqueTargetTempHeatingStrategy";
	}

	@Override
	public void OnActivated(AbstractHeatingParameters heatingParams) {
		UniqueTargetTempHeatingParameters par = (UniqueTargetTempHeatingParameters)heatingParams;
		_tempControl.StartHeating(par.getTargetTemperature());
	}

	@Override
	public void OnDeactivated() {
		// no cleanup needed
	}

	@Override
	public void OnCurrentTemperatureChanged(double temp) {
		// no handling needed
	}
}
