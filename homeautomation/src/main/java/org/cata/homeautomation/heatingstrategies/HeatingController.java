package org.cata.homeautomation.heatingstrategies;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.cata.homeautomation.contracts.IHeaterUnit;
import org.cata.homeautomation.contracts.IHeatingController;
import org.cata.homeautomation.contracts.IHeatingStrategy;
import org.cata.homeautomation.contracts.ITemperatureControl;
import org.cata.homeautomation.contracts.ITemperatureSensor;
import org.cata.homeautomation.dataobjects.AbstractHeatingParameters;
import org.cata.homeautomation.dataobjects.HeaterState;
import org.cata.homeautomation.dataobjects.TempChangedEvent;

import com.google.common.eventbus.EventBus;

import rx.Subscription;
import rx.functions.Action0;
import rx.schedulers.Schedulers;

public class HeatingController implements IHeatingController {

	private EventBus _eventBus;
	private IHeaterUnit _heaterUnit;
	private ITemperatureSensor _tempSensor;
	private ITemperatureControl _tempControl;
	private List<IHeatingStrategy> _heatingStrategies;
	
	private IHeatingStrategy _currentHeatingStrategy;
	
	private final int PollingInterval = 2000;
	
	private Subscription temperaturePollingSubscription;
	private double currentTemp;
	
	public HeatingController(EventBus eventBus, IHeaterUnit heaterUnit, ITemperatureSensor temperatureSensor, ITemperatureControl tempControl,
			List<IHeatingStrategy> heatingStrategies) {
		_eventBus = eventBus;
		_heaterUnit = heaterUnit;
		_tempSensor = temperatureSensor;
		_tempControl = tempControl;
		_heatingStrategies = heatingStrategies;
		
		temperaturePollingSubscription = Schedulers.computation().createWorker().schedulePeriodically(new Action0() {
            @Override
            public void call() {
            	currentTemp = _tempSensor.GetTemperature();
            	
            	TempChangedEvent ev = new TempChangedEvent();
            	ev.setCurrentTemperature(currentTemp);
            	eventBus.post(ev);
            	if (_currentHeatingStrategy != null) {
            		_currentHeatingStrategy.OnCurrentTemperatureChanged(currentTemp);
            	}
            	_tempControl.OnTemperatureChanged(currentTemp);
            }
        }, PollingInterval, PollingInterval, TimeUnit.MILLISECONDS);
	}

	@Override
	public void ChangeHeatingStrategy(String strategyName, AbstractHeatingParameters heatingParams) {
		// TODO Auto-generated method stub
		if (_currentHeatingStrategy != null) {
			_currentHeatingStrategy.OnDeactivated();
		}
		_currentHeatingStrategy = _heatingStrategies.get(0);
		heatingParams.setCurrentTemp(currentTemp);
		_currentHeatingStrategy.OnActivated(heatingParams);
	}

	@Override
	public HeaterState GetHeaterState() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
