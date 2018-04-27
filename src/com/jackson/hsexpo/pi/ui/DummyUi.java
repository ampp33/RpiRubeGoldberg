package com.jackson.hsexpo.pi.ui;

import java.io.IOException;

import com.jackson.hsexpo.pi.PhototransistorGate;
import com.jackson.hsexpo.pi.controller.RubeGoldbergHardwareController;
import com.jackson.hsexpo.pi.listeners.PhototransistorGateListener;
import com.jackson.hsexpo.pi.listeners.RubeGoldbergButtonListener;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DummyUi extends Application {
	
	private static final String DEACTIVATED_GATE_TEXT = "Deactivated";
	private static final String ACTIVATED_GATE_TEXT = "Activated!";
	private static final Color DEACTIVATED_GATE_COLOR = Color.RED;
	private static final Color ACTIVATED_GATE_COLOR = Color.GREEN;
	
	private RubeGoldbergHardwareController hardwareController;
	
	private int lowestGateClosed;
	private int numberOfAttempts = 1;
	private long gameStartTime = 0;
	
	private TextFlow flow;
	
	private Text[] gateIndicatorTextObjects = null;
	
	private Text numAttemptsIndicatorText;
	private Text successMessage;
	private Text statsLabel;
	
	private String PATH_TO_FONT = "retro.ttf";
	private int FONT_SIZE = 80;
	private int SMALL_FONT_SIZE = 50;
	private Font defaultFont = null;
	private Font smallDefaultFont = null;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		defaultFont = Font.loadFont(DummyUi.class.getResourceAsStream(PATH_TO_FONT), FONT_SIZE);
		smallDefaultFont = Font.loadFont(DummyUi.class.getResourceAsStream(PATH_TO_FONT), SMALL_FONT_SIZE);
		initializeWindow(primaryStage);
		initializeHardware();
	}
	
	private Text createText(String text, Color color) {
		Text textObj = new Text(text);
		textObj.setFill(color);
		textObj.setFont(defaultFont);
		return textObj;
	}
	
	private void initializeWindow(Stage stage) throws IOException, InterruptedException {
		
		Text numAttemptsLabel = createText("Number of attempts: ", Color.WHITE);
		numAttemptsIndicatorText = createText(Integer.toString(numberOfAttempts), Color.YELLOW);
		
		Text gate1Label = createText("\nGate 1: ", Color.WHITE);
		Text gate1IndicatorText = createText(DEACTIVATED_GATE_TEXT, DEACTIVATED_GATE_COLOR);
		
		Text gate2Label = createText("\nGate 2: ", Color.WHITE);
		Text gate2IndicatorText = createText(DEACTIVATED_GATE_TEXT, DEACTIVATED_GATE_COLOR);
		
		Text gate3Label = createText("\nGate 3: ", Color.WHITE);
		Text gate3IndicatorText = createText(DEACTIVATED_GATE_TEXT, DEACTIVATED_GATE_COLOR);
		
		Text gate4Label = createText("\nGate 4: ", Color.WHITE);
		Text gate4IndicatorText = createText(DEACTIVATED_GATE_TEXT, DEACTIVATED_GATE_COLOR);
		
		gateIndicatorTextObjects = new Text[] {gate1IndicatorText, gate2IndicatorText, gate3IndicatorText, gate4IndicatorText};
		
		successMessage = createText("\nAll Gates Activated!  Success!", Color.GREEN);
		successMessage.setVisible(false);
		
		statsLabel = createText("\n", Color.WHITE);
		statsLabel.setVisible(false);
		
		flow = new TextFlow();
		flow.setTextAlignment(TextAlignment.LEFT);
		flow.setPrefSize(600, 600);
		flow.setLineSpacing(3.0);
		flow.setPadding(new Insets(10, 10, 10, 10));
		flow.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		flow.getChildren().add(numAttemptsLabel);
		flow.getChildren().add(numAttemptsIndicatorText);
		flow.getChildren().add(gate1Label);
		flow.getChildren().add(gate1IndicatorText);
		flow.getChildren().add(gate2Label);
		flow.getChildren().add(gate2IndicatorText);
		flow.getChildren().add(gate3Label);
		flow.getChildren().add(gate3IndicatorText);
		flow.getChildren().add(gate4Label);
		flow.getChildren().add(gate4IndicatorText);
		flow.getChildren().add(successMessage);
		
		Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(200),
                event -> {
//                	successMessage.setVisible(!successMessage.isVisible());
                	// screen refresh event
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
		
        Scene scene = new Scene(flow);
        
        // manual keyboard testing harness
//        scene.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
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
//        		resetGameHandler();
//        	}
//        	if(gateNum > 0) {
//        		Mockito.when(gate.getGateNumber()).thenReturn(gateNum);
//        		gateTriggerHandler(gate);
//        	}
//        });
        
        stage.setFullScreenExitHint(""); // don't show the "press esc to exit full screen message"
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
	}
	
	private void initializeHardware() {
		lowestGateClosed = 0;
		numberOfAttempts = 0;
		hardwareController = new RubeGoldbergHardwareController();
		hardwareController.addGateTriggerListener(new PhototransistorGateListener() {
			@Override
			public void gateTriggered(PhototransistorGate gate) {
				gateTriggerHandler(gate);
			}
		});
		hardwareController.addButtonTriggerListener(new RubeGoldbergButtonListener() {
			@Override
			public void resetGame() {
				resetGameHandler();
			}
			@Override
			public void markGameCompleted() {
				markGameCompletedHandler();
			}
		});
	}
	
	private void signalError(String errorMessage) {
		resetGameHandler();
	}
	
	private void resetGameHandler() {
		lowestGateClosed = 0;
		numberOfAttempts++;
		for (Text gateIndicatorText : gateIndicatorTextObjects) {
			gateIndicatorText.setText(DEACTIVATED_GATE_TEXT);
			gateIndicatorText.setFill(DEACTIVATED_GATE_COLOR);
		}
		successMessage.setVisible(false);
		statsLabel.setVisible(false);
		numAttemptsIndicatorText.setText(Integer.toString(numberOfAttempts));
	}
	
	private void markGameCompletedHandler() {
		successMessage.setVisible(true);
		
		// figure out stat text
		long timeToComplete = System.currentTimeMillis() - gameStartTime;
		java.time.Duration duration = java.time.Duration.ofMillis(timeToComplete);
		long minutesPart = duration.toMinutes();
		long secondsPart = duration.minusMinutes(minutesPart).getSeconds();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append("\nTime to complete: ");
		buffer.append(minutesPart);
		buffer.append(" minutes");
		buffer.append(", ");
		buffer.append(secondsPart);
		buffer.append(" seconds");
		
		statsLabel.setText(buffer.toString());
		statsLabel.setVisible(true);
	}
	
	private void gateTriggerHandler(PhototransistorGate gate) {
		// if this is our first gate triggering, the game has begun!
		if(numberOfAttempts == 1) {
			gameStartTime = System.currentTimeMillis();
		}
		if(gate.getGateNumber() == lowestGateClosed + 1) {
			// next gate in order was activated
			lowestGateClosed++;
		} else if(gate.getGateNumber() == 1) {
			// first gate was activated instead of the next assumed gate,
			// suggesting that the users are retrying the game
			resetGameHandler();
			lowestGateClosed = 1;
		} else {
			// gate activated was not the next expected sequential gate, signal error
			signalError("Gate " + gate.getGateNumber() + " activated, but gate " + (lowestGateClosed + 1) + " was expected!  Resetting game...");
			return;
		}
		gateIndicatorTextObjects[lowestGateClosed - 1].setText(ACTIVATED_GATE_TEXT);
		gateIndicatorTextObjects[lowestGateClosed - 1].setFill(ACTIVATED_GATE_COLOR);
		
		if(lowestGateClosed == 4) {
			// all gates closed!  signal success!
			markGameCompletedHandler();
		}
	}

}
