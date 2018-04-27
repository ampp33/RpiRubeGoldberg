package com.jackson.hsexpo.pi.listeners;

import com.jackson.hsexpo.pi.TriggerablePin;

public interface TriggerablePinListener {
	
	public void triggerHigh(TriggerablePin triggerablePin);
	public void triggerLow(TriggerablePin triggerablePin);
	
}
