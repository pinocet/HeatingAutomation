package org.cata.homeautomation.heatingstrategies;

import org.cata.homeautomation.contracts.IHeatingStrategy;
import org.cata.homeautomation.contracts.ITemperatureControl;
import org.cata.homeautomation.dataobjects.AbstractHeatingParameters;

/**
 * Strategy: poll every 10 mins for temperature.
 * If it goes below 7 deg C, start heating until temp climbs to 9 deg C.
 * 
 */
public class AntiFreezeHeatingStrategy extends AbstractHeatingStrategy implements IHeatingStrategy {

	private double ThresholdTemp = 7.0d;
	private double TargetTempWhenBelowThreshold = 9.0d;
	
	public AntiFreezeHeatingStrategy(ITemperatureControl tempControl)
	{
		super(tempControl);
	}
	
	@Override
	public void OnActivated(AbstractHeatingParameters heatingParams) {
		if (heatingParams.getCurrentTemp() < ThresholdTemp) {
			_tempControl.StartHeating(TargetTempWhenBelowThreshold);
		}
	}

	@Override
	public void OnDeactivated() {
		// no action needed
	}

	@Override
	public void OnCurrentTemperatureChanged(double temp) {
		if (temp < ThresholdTemp) {
			_tempControl.StartHeating(TargetTempWhenBelowThreshold);
		}
	}

	@Override
	public String GetName() {
		return "AntiFreezeHeatingStrategy";
	}

}
