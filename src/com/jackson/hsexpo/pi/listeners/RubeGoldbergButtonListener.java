package com.jackson.hsexpo.pi.listeners;

public abstract class RubeGoldbergButtonListener extends ButtonListener {
	
	public abstract void resetGame();
	public abstract void markGameCompleted();
	
	private long buttonDownTime;
	private boolean readyToMarkGameCompleted = true;

	@Override
	public void buttonUp() {
		long buttonDownDurationInMs = System.currentTimeMillis() - buttonDownTime;
		if(buttonDownDurationInMs < 100) {
			// debounce
		} else if(buttonDownDurationInMs < 2000) {
			readyToMarkGameCompleted = false;
			resetGame();
		} else if (buttonDownDurationInMs > 5000) {
			if(!readyToMarkGameCompleted) {
				readyToMarkGameCompleted = true;
			} else {
				// TODO: maybe also require gate 1 to be blocked?
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
