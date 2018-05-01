package com.jackson.hsexpo.pi.ui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
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

public class TextDisplayController {
	
	// constants
	private static final String DEACTIVATED_GATE_TEXT = "Deactivated";
	private static final String ACTIVATED_GATE_TEXT = "Activated!";
	private static final Color DEACTIVATED_GATE_COLOR = Color.RED;
	private static final Color ACTIVATED_GATE_COLOR = Color.GREEN;
	private static final String RETRO_FONT = "retro.ttf";
	private static final String TERMINUS_FONT = "terminus.ttf";
	private static final int HUGE_FONT_SIZE = 240;
	private static final int LARGE_FONT_SIZE = 80;
	private static final int SMALL_FONT_SIZE = 50;
	private Font DEFAULT_FONT = Font.loadFont(DummyUi.class.getResourceAsStream(RETRO_FONT), LARGE_FONT_SIZE);
	private Font SMALL_DEFAULT_FONT = Font.loadFont(DummyUi.class.getResourceAsStream(RETRO_FONT), SMALL_FONT_SIZE);
	private Font ANIMATION_FONT = Font.loadFont(DummyUi.class.getResourceAsStream(TERMINUS_FONT), HUGE_FONT_SIZE);
	private static final double SCREEN_REFRESH_RATE = 100;
	private static final double ANIMATION_REFRESH_RATE = 800;
	private static final String[] ANIMATION_FRAMES = new String[] { "( •_•)", "( •_•)>", "( •_•)>⌐■-■", "(⌐■_■)<" };
	
	// ui elements
	private Text[] mainScreenTextObjects = null;
	private Text[] gateIndicatorTextObjects = null;
	private Text numAttemptsIndicatorText;
	private Text successMessage;
	private Text runStatsLabel;
	private Text gameStatsLabel;
	private Text animationLabel;
	
	private TextFlow flow;
	
	// animation
	private boolean playWinAnimation = false;
	private int animationFrameNum = 0;
	private int animationFrameCount = 0;
	
	public TextDisplayController(Stage stage) {
		flow = new TextFlow();
		flow.setTextAlignment(TextAlignment.LEFT);
		flow.setPrefSize(600, 600);
		flow.setLineSpacing(3.0);
		flow.setPadding(new Insets(5, 10, 5, 10));
		flow.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
		
		// create full screen scene
		Scene scene = new Scene(flow);
		stage.setFullScreenExitHint(""); // don't show the "press esc to exit full screen message"
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.show();
		
        // setup 
		Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.millis(SCREEN_REFRESH_RATE),
                event -> {
                	screenRefreshHandler(event);
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        
        initializeMainScreenObjects();
	}
	
	public void displayMainScreen() {
		flow.getChildren().clear();
		for (Text mainScreenText : mainScreenTextObjects) {
			flow.getChildren().add(mainScreenText);
		}
	}

	public void resetMainScreen() {
		for (Text gateIndicatorText : gateIndicatorTextObjects) {
			gateIndicatorText.setText(DEACTIVATED_GATE_TEXT);
			gateIndicatorText.setFill(DEACTIVATED_GATE_COLOR);
		}
		successMessage.setVisible(false);
		runStatsLabel.setVisible(false);
		gameStatsLabel.setVisible(false);
		animationLabel.setVisible(false);
		playWinAnimation = false;
	}
	
	public void setNumberOfAttempts(int numberOfAttempts) {
		numAttemptsIndicatorText.setText(Integer.toString(numberOfAttempts));
	}
	
	public void setGateActivated(int gateNumber) {
		gateIndicatorTextObjects[gateNumber - 1].setText(ACTIVATED_GATE_TEXT);
		gateIndicatorTextObjects[gateNumber - 1].setFill(ACTIVATED_GATE_COLOR);
	}
	
	public void playGameCompletedScreen(long runDuration, long gameDuration) {
		successMessage.setVisible(true);
		
		// figure out run stat text
		runStatsLabel.setText("\nGate 1 to 4 time: " + getDurationText(runDuration));
		runStatsLabel.setVisible(true);
		
		// figure out game stat text
		gameStatsLabel.setText("\nTotal play time: " + getDurationText(gameDuration));
		gameStatsLabel.setVisible(true);
		
		animationLabel.setVisible(true);
		animationFrameNum = 0;
		animationFrameCount = 0;
		playWinAnimation = true;
	}
	
	private void screenRefreshHandler(ActionEvent event) {
		if(playWinAnimation) {
			// animation shouldn't play at the same rate as the screen refresh rate, so slow it down a bit
			// and only update the animation at the animation refresh rate
			if(animationFrameCount == 0) {
				// update animation
				animationLabel.setText("\n" + ANIMATION_FRAMES[animationFrameNum]);
				// increment animation frame number
				animationFrameNum = (animationFrameNum + 1) % ANIMATION_FRAMES.length;
			}
			animationFrameCount = (int)((animationFrameCount + SCREEN_REFRESH_RATE) % ANIMATION_REFRESH_RATE);
		}
	}
	
	private void initializeMainScreenObjects() {
		Text numAttemptsLabel = createText("Number of attempts: ", Color.WHITE);
		numAttemptsIndicatorText = createText(Integer.toString(1), Color.YELLOW);
		
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
		
		runStatsLabel = createSmallText("\n", Color.WHITE);
		runStatsLabel.setVisible(false);
		
		gameStatsLabel = createSmallText("\n", Color.WHITE);
		gameStatsLabel.setVisible(false);
		
		animationLabel = createAnimationText("\n", Color.WHITE);
		animationLabel.setVisible(false);
		playWinAnimation = false;
		
		// line spacing fixes
		successMessage.setTranslateY(-120);
		runStatsLabel.setTranslateY(-180);
		gameStatsLabel.setTranslateY(-180);
		animationLabel.setTranslateY(-180);
		
		mainScreenTextObjects = new Text[] { numAttemptsLabel, numAttemptsIndicatorText, gate1Label, gate1IndicatorText,
												gate2Label, gate2IndicatorText,
												gate3Label, gate3IndicatorText,
												gate4Label, gate4IndicatorText,
												successMessage, animationLabel, runStatsLabel, gameStatsLabel };
	}
	
	private String getDurationText(long durationInMillis) {
		java.time.Duration duration = java.time.Duration.ofMillis(durationInMillis);
		long minutesPart = duration.toMinutes();
		long secondsPart = duration.minusMinutes(minutesPart).getSeconds();
		long millisPart = duration.minusMinutes(minutesPart).minusSeconds(secondsPart).toMillis();
		
		StringBuffer buffer = new StringBuffer();
		buffer.append(minutesPart);
		buffer.append(" minutes");
		buffer.append(", ");
		buffer.append(secondsPart);
		buffer.append(" seconds");
		buffer.append(", ");
		buffer.append(millisPart);
		buffer.append(" ms");
		
		return buffer.toString();
	}
	
	private Text createText(String text, Color color) {
		Text textObj = new Text(text);
		textObj.setFill(color);
		textObj.setFont(DEFAULT_FONT);
		return textObj;
	}
	
	private Text createSmallText(String text, Color color) {
		Text textObj = createText(text, color);
		textObj.setFont(SMALL_DEFAULT_FONT);
		return textObj;
	}
	
	private Text createAnimationText(String text, Color color) {
		Text textObj = createText(text, color);
		textObj.setFont(ANIMATION_FONT);
		return textObj;
	}
	
}
