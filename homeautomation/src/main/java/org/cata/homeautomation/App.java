package org.cata.homeautomation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cata.homeautomation.contracts.IHeaterUnit;
import org.cata.homeautomation.contracts.IHeatingController;
import org.cata.homeautomation.contracts.IHeatingStrategy;
import org.cata.homeautomation.contracts.ITemperatureControl;
import org.cata.homeautomation.contracts.ITemperatureSensor;
import org.cata.homeautomation.dataobjects.UniqueTargetTempHeatingParameters;
import org.cata.homeautomation.fake.FakeHeaterUnit;
import org.cata.homeautomation.fake.FakeTemperatureSensor;
import org.cata.homeautomation.heatingstrategies.HeatingController;
import org.cata.homeautomation.heatingstrategies.TemperatureControl;
import org.cata.homeautomation.heatingstrategies.UniqueTargetTempHeatingStrategy;

import com.google.common.eventbus.EventBus;

public class App 
{
    public static void main( String[] args )
    {
    	EventBus eventBus = new EventBus();
    	
    	IHeaterUnit heaterUnit = new FakeHeaterUnit();
    	ITemperatureSensor tempSensor = new FakeTemperatureSensor();
    	ITemperatureControl tempControl = new TemperatureControl(heaterUnit);
    	
    	List<IHeatingStrategy> heatingStrategies = new ArrayList<IHeatingStrategy>();
    	UniqueTargetTempHeatingStrategy uniqueTempHeatingStrategy = new UniqueTargetTempHeatingStrategy(tempControl);
    	heatingStrategies.add(uniqueTempHeatingStrategy);
    	
    	IHeatingController heatingController = new HeatingController(eventBus, heaterUnit, tempSensor, tempControl, heatingStrategies);
    	
    	HeatingAutomationApi api = new HeatingAutomationApi(eventBus, heatingController);
    	
    	eventBus.register(api);
    	
    	UniqueTargetTempHeatingParameters params = new UniqueTargetTempHeatingParameters();
    	heatingController.ChangeHeatingStrategy("UniqueTargetTempHeatingStrategy", params);
    	api.SetupRouter();
    	api.SetupClient();
    	
    	waitUntilKeypressed();
    	api.Dispose();
    }
    
    private static void waitUntilKeypressed() {
        try {
            System.in.read();
            while (System.in.available() > 0) {
                System.in.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
