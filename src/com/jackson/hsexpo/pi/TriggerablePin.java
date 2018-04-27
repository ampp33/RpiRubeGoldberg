package com.jackson.hsexpo.pi;

import com.jackson.hsexpo.pi.listeners.TriggerablePinListener;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.event.GpioPinDigitalStateChangeEvent;
import com.pi4j.io.gpio.event.GpioPinListenerDigital;

public class TriggerablePin {
	
	private GpioPinDigitalInput gatePin;
	
	public TriggerablePin(GpioController gpio, Pin pin) {
		gatePin = gpio.provisionDigitalInputPin(pin, PinPullResistance.PULL_UP); // TODO change back?
		gatePin.setShutdownOptions(true, PinState.LOW, PinPullResistance.OFF);
	}
	
	public void addEventListener(TriggerablePinListener listener) {
		final TriggerablePin thisPin = this;
		gatePin.addListener(new GpioPinListenerDigital() {
			@Override
			public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
				if (event.getState().isHigh()) {
					listener.triggerHigh(thisPin);
				} else {
					listener.triggerLow(thisPin);
				}
			}
		});
	}
	
}