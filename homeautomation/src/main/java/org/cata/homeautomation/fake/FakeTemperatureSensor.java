package org.cata.homeautomation.fake;

import java.util.Random;

import org.cata.homeautomation.contracts.ITemperatureSensor;

public class FakeTemperatureSensor implements ITemperatureSensor {

	private Random rand;
	
	public FakeTemperatureSensor() {
		rand = new Random();
	}
	
	@Override
	public double GetTemperature() {
		return rand.nextInt(30) + rand.nextDouble();
	}

	@Override
	public String GetSensorName() {
		return "FakeTempSensor";
	}
}
