package org.cata.homeautomation.fake;

import org.cata.homeautomation.contracts.IHeaterUnit;

public class FakeHeaterUnit implements IHeaterUnit {

	private boolean _isOn;
	
	@Override
	public void Start() {
		// nothing to do
	}

	@Override
	public void Stop() {
		// nothing to do
	}

	@Override
	public boolean IsOn() {
		return _isOn;
	}

	@Override
	public String GetName() {
		return "FakeHeaterUnit";
	}

}
