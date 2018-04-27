package com.jackson.hsexpo.pi;

import com.jackson.hsexpo.pi.listeners.TriggerablePinListener;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.Pin;

public class PhototransistorGate extends TriggerablePin {
	
	private int gateNumber;
	
	public PhototransistorGate(GpioController gpio, Pin pin, int gateNumber) {
		super(gpio, pin);
		this.gateNumber = gateNumber;
	}
	
	public void addEventListener(TriggerablePinListener triggerListener) {
		super.addEventListener(triggerListener);
	}
	
	public int getGateNumber() {
		return gateNumber;
	}
	
}