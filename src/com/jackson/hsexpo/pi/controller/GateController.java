package com.jackson.hsexpo.pi.controller;

import java.util.ArrayList;
import java.util.List;

import com.jackson.hsexpo.pi.PhototransistorGate;
import com.jackson.hsexpo.pi.listeners.PhototransistorGateListener;
import com.pi4j.io.gpio.GpioController;

public class GateController {
	
	private List<PhototransistorGate> gates = new ArrayList<>();
	
	public GateController(GpioController gpio) {
		gates.add(new PhototransistorGate(gpio, RubeGoldbergConstants.GATE_1_PIN, 1));
		gates.add(new PhototransistorGate(gpio, RubeGoldbergConstants.GATE_2_PIN, 2));
		gates.add(new PhototransistorGate(gpio, RubeGoldbergConstants.GATE_3_PIN, 3));
		gates.add(new PhototransistorGate(gpio, RubeGoldbergConstants.GATE_4_PIN, 4));
	}
	
	public void addGateTriggerListener(PhototransistorGateListener gateListener) {
		for (PhototransistorGate phototransistorGate : gates) {
			phototransistorGate.addEventListener(gateListener);
		}
	}
	
}
