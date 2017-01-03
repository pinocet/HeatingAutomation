package org.cata.homeautomation.dataobjects;

public abstract class AbstractHeatingParameters {
	private double currentTemp;

	public double getCurrentTemp() {
		return currentTemp;
	}

	public void setCurrentTemp(double currentTemp) {
		this.currentTemp = currentTemp;
	}

}
