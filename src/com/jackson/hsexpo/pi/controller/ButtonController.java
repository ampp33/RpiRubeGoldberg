package com.jackson.hsexpo.pi.controller;

import com.jackson.hsexpo.pi.TriggerablePin;
import com.jackson.hsexpo.pi.listeners.ButtonListener;
import com.pi4j.io.gpio.GpioController;

public class ButtonController {
	
	private TriggerablePin buttonPin;
	
	public ButtonController(GpioController gpio) {
		buttonPin = new TriggerablePin(gpio, RubeGoldbergConstants.BUTTON_PIN);
	}
	
	public void addButtonTriggerListener(ButtonListener buttonListener) {
		buttonPin.addEventListener(buttonListener);
	}
	
}
