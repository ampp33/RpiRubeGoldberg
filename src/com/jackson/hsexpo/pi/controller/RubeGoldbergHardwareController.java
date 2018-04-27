package com.jackson.hsexpo.pi.controller;

import com.jackson.hsexpo.pi.listeners.ButtonListener;
import com.jackson.hsexpo.pi.listeners.PhototransistorGateListener;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;

public class RubeGoldbergHardwareController {
	
	private GateController gateController;
	private ButtonController buttonController;
	private GpioController gpio;
	
	public RubeGoldbergHardwareController() {
		gpio = GpioFactory.getInstance();
		gateController = new GateController(gpio);
		buttonController = new ButtonController(gpio);
	}
	
	public void addGateTriggerListener(PhototransistorGateListener gateListener) {
		gateController.addGateTriggerListener(gateListener);
	}
	
	public void addButtonTriggerListener(ButtonListener buttonListener) {
		buttonController.addButtonTriggerListener(buttonListener);
	}
	
	public void shutdown() {
		gpio.shutdown();
	}
	
}
