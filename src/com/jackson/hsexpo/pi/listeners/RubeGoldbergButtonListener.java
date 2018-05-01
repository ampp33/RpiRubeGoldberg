package com.jackson.hsexpo.pi.listeners;

public abstract class RubeGoldbergButtonListener extends ButtonListener {
	
	private static final long DEBOUNCE_INTERVAL = 80;
	
	public abstract void resetGame();
	public abstract void fullResetGame();
	public abstract void markGameCompleted();
	
	private long buttonDownTime;
	private boolean readyToMarkGameCompleted = true;

	@Override
	public void buttonUp() {
		long buttonPressDurationInMs = System.currentTimeMillis() - buttonDownTime;
		if(buttonPressDurationInMs < DEBOUNCE_INTERVAL) {
			// debounce, ignore input
		} else if(buttonPressDurationInMs < 1000) {
			readyToMarkGameCompleted = false;
			resetGame();
		} else if (buttonPressDurationInMs > 5000 && buttonPressDurationInMs < 10000) {
			fullResetGame();
		} else if (buttonPressDurationInMs > 10000) {
			if(!readyToMarkGameCompleted) {
				readyToMarkGameCompleted = true;
			} else {
				markGameCompleted();
				readyToMarkGameCompleted = false;
			}
		}
	}

	@Override
	public void buttonDown() {
		buttonDownTime = System.currentTimeMillis();
	}
	
}
