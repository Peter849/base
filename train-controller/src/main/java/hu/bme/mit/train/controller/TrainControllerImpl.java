package hu.bme.mit.train.controller;

import hu.bme.mit.train.interfaces.TrainController;

/**
 * Primary controller implementation
 */
public class TrainControllerImpl implements TrainController {

	private int step = 0;
	private int referenceSpeed = 0;
	private int speedLimit = 0;
	private boolean move = false;
	// In seconds
	private static int timeLimit = 5;

	@Override
	public void followSpeed() {
		if(!eBreak){
			if (referenceSpeed < 0) {
				referenceSpeed = 0;
			} else {
				if(referenceSpeed+step > 0) {
					referenceSpeed += step;
				} else {
					referenceSpeed = 0;
				}
			}
		}else{
			if(referenceSpeed > 0){
				if(step > 0)referenceSpeed -= step;
				else referenceSpeed += step;
				if(referenceSpeed < 0) referenceSpeed = 0;
			}else{
				this.eBreak = false;
			}
		}
		
		enforceSpeedLimit();
	}

	@Override
	public void runSimulation() throws InterruptedException {
		while(move) {
			followSpeed();
			Thread.sleep(timeLimit * 1000);
		}
	}

	@Override
	public void startSimulation() {
		move = true;
	}

	@Override
	public void stopSimulation() {
		move = false;
	}

	@Override
	public int getReferenceSpeed() {
		return referenceSpeed;
	}

	@Override
	public void setSpeedLimit(int speedLimit) {
		this.speedLimit = speedLimit;
		enforceSpeedLimit();
		
	}

	private void enforceSpeedLimit() {
		if (referenceSpeed > speedLimit) {
			referenceSpeed = speedLimit;
		}
	}

	@Override
	public void setJoystickPosition(int joystickPosition) {
		this.step = joystickPosition;		
	}

	@Override
	public void emergencyBreak(){
		this.eBreak = true;
	}
}