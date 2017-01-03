package org.cata.homeautomation.dataobjects;

public class HeaterState {
	private double currentTemperature;
	private boolean heatingOn;
	private String heatingStrategy;
	
	public double getCurrentTemperature() {
		return currentTemperature;
	}
	public void setCurrentTemperature(double currentTemperature) {
		this.currentTemperature = currentTemperature;
	}
	public boolean isHeatingOn() {
		return heatingOn;
	}
	public void setHeatingOn(boolean heatingOn) {
		this.heatingOn = heatingOn;
	}
	public String getHeatingStrategy() {
		return heatingStrategy;
	}
	public void setHeatingStrategy(String heatingStrategy) {
		this.heatingStrategy = heatingStrategy;
	}
}
