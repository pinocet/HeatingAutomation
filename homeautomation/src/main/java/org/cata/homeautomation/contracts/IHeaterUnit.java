package org.cata.homeautomation.contracts;

public interface IHeaterUnit {
	
	void Start();
	
	void Stop();
	
	boolean IsOn();
	
	String GetName();

}
