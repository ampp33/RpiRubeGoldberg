package com.jackson.hsexpo.pi.ui;

import java.io.IOException;

import com.jackson.hsexpo.pi.PhototransistorGate;
import com.jackson.hsexpo.pi.controller.RubeGoldbergHardwareController;
import com.jackson.hsexpo.pi.listeners.PhototransistorGateListener;
import com.jackson.hsexpo.pi.listeners.RubeGoldbergButtonListener;

import javafx.application.Application;
import javafx.stage.Stage;

public class DummyUi extends Application {
	
	// hardware controller
	private RubeGoldbergHardwareController hardwareController;
	
	// screen controller
	private TextDisplayController textDisplayController;
	
	// game variables
	private int lowestGateClosed;
	private int numberOfAttempts = 0;
	private long gameStartTime;
	private long runStartTime;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		initializeWindow(primaryStage);
		initializeHardware();
	}
	
	private void initializeWindow(Stage stage) throws IOException, InterruptedException {
		textDisplayController = new TextDisplayController(stage);
		textDisplayController.displayMainScreen();
        
        // manual keyboard testing harness
//        stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
//        	int gateNum = 0;
//        	PhototransistorGate gate = Mockito.mock(PhototransistorGate.class);
//        	if(keyEvent.getCode() == KeyCode.DIGIT1) {
//        		gateNum = 1;
//        	} else if (keyEvent.getCode() == KeyCode.DIGIT2) {
//        		gateNum = 2;
//        	} else if (keyEvent.getCode() == KeyCode.DIGIT3) {
//        		gateNum = 3;
//        	} else if (keyEvent.getCode() == KeyCode.DIGIT4) {
//        		gateNum = 4;
//        	} else if (keyEvent.getCode() == KeyCode.B) {
//        		resetGameHandler(false);
//        	}
//        	if(gateNum > 0) {
//        		Mockito.when(gate.getGateNumber()).thenReturn(gateNum);
//        		gateTriggerHandler(gate);
//        	}
//        });
	}
	
	private void initializeHardware() {
		lowestGateClosed = 0;
		numberOfAttempts = 0;
		hardwareController = new RubeGoldbergHardwareController();
		hardwareController.addGateTriggerListener(new PhototransistorGateListener() {
			@Override
			public void gateTriggered(PhototransistorGate gate) {
				wakeScreen();
				gateTriggerHandler(gate);
			}
		});
		hardwareController.addButtonTriggerListener(new RubeGoldbergButtonListener() {
			@Override
			public void resetGame() {
				wakeScreen();
				resetGameHandler(false);
			}
			@Override
			public void fullResetGame() {
				wakeScreen();
				resetGameHandler(true);
			}
			@Override
			public void markGameCompleted() {
				wakeScreen();
				markGameCompletedHandler();
			}
		});
	}
	
	private void gateTriggerHandler(PhototransistorGate gate) {
		// if this is our first gate triggering, the game has begun!
		if(numberOfAttempts == 0) {
			numberOfAttempts++;
			gameStartTime = System.currentTimeMillis();
		}
		if(gate.getGateNumber() == lowestGateClosed + 1) {
			// next gate in order was activated
			lowestGateClosed++;
		} else if(gate.getGateNumber() == 1) {
			// first gate was activated instead of the next assumed gate,
			// suggesting that the users are retrying the game
			resetGameHandler(false);
			lowestGateClosed = 1;
		} else {
			// gate activated was not the next expected sequential gate, signal error
			signalError("Gate " + gate.getGateNumber() + " activated, but gate " + (lowestGateClosed + 1) + " was expected!  Resetting game...");
			return;
		}
		
		if(lowestGateClosed == 1) {
			// start timing how long it takes to get to the end of the puzzle
			runStartTime = System.currentTimeMillis();
		}
		
		textDisplayController.setGateActivated(lowestGateClosed);
		
		if(lowestGateClosed == 4) {
			// all gates closed!  signal success!
			markGameCompletedHandler();
		}
	}
	
	private void resetGameHandler(boolean fullReset) {
		lowestGateClosed = 0;
		if(fullReset) {
			numberOfAttempts = 0;
		} else {
			numberOfAttempts++;
		}
		textDisplayController.resetMainScreen();
		textDisplayController.setNumberOfAttempts(numberOfAttempts);
	}
	
	private void markGameCompletedHandler() {
		long currentTime = System.currentTimeMillis();
		long runDuration = currentTime - runStartTime;
		long gameDuration = currentTime - gameStartTime;
		textDisplayController.playGameCompletedScreen(runDuration, gameDuration);
	}
	
	private void signalError(String errorMessage) {
		resetGameHandler(false);
	}
	
	private void wakeScreen() {
//		robot.keyPress(java.awt.event.KeyEvent.VK_J);
	}

}
