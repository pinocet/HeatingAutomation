package org.cata.homeautomation.contracts;

import org.cata.homeautomation.dataobjects.AbstractHeatingParameters;
import org.cata.homeautomation.dataobjects.HeaterState;

public interface IHeatingController {

	void ChangeHeatingStrategy(String strategyName, AbstractHeatingParameters heatingParams);
	
	HeaterState GetHeaterState();
	
}
