package org.cata.homeautomation.heatingstrategies;

import org.cata.homeautomation.contracts.IHeaterUnit;
import org.cata.homeautomation.contracts.ITemperatureControl;

public class TemperatureControl implements ITemperatureControl {

	private double TempTolerance = 0.1d;
	
	private double _targetTemperature;
	private boolean _heatingOn;
	
	private IHeaterUnit _heaterUnit;
	
	public TemperatureControl(IHeaterUnit heaterUnit) {
		_heaterUnit = heaterUnit;
	}	
	
	@Override
	public void StartHeating(double targetTemperature) {
		_targetTemperature = targetTemperature;
		_heatingOn = true;
		
		if (!_heaterUnit.IsOn()) {
			_heaterUnit.Start();
		}
	}

	@Override
	public void OnTemperatureChanged(double currentTemp) {
		if (_heatingOn && Math.abs(currentTemp - _targetTemperature) < TempTolerance) {
			StopHeating();
		}
	}

	@Override
	public void StopHeating() {
		_heatingOn = false;
		_heaterUnit.Stop();
	}
}
