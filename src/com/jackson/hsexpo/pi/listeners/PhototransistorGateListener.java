package com.jackson.hsexpo.pi.listeners;

import com.jackson.hsexpo.pi.PhototransistorGate;
import com.jackson.hsexpo.pi.TriggerablePin;

public abstract class PhototransistorGateListener implements TriggerablePinListener {
	
	public abstract void gateTriggered(PhototransistorGate gate);

	@Override
	public void triggerHigh(TriggerablePin triggerablePin) {
		gateTriggered((PhototransistorGate)triggerablePin);
	}
	
	@Override
	public void triggerLow(TriggerablePin triggerablePin) {}

}
