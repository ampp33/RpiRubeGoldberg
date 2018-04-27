package com.jackson.hsexpo.pi.listeners;

import com.jackson.hsexpo.pi.TriggerablePin;

public abstract class ButtonListener implements TriggerablePinListener {
	
	public abstract void buttonUp();
	public abstract void buttonDown();

	@Override
	public void triggerHigh(TriggerablePin triggerablePin) {
		buttonUp();
	}

	@Override
	public void triggerLow(TriggerablePin triggerablePin) {
		buttonDown();
	}
	
}
