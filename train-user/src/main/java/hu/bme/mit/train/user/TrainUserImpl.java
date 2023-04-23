package hu.bme.mit.train.user;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;

public class TrainUserImpl implements TrainUser {

	private TrainController controller;
	private int joystickPosition;
	private boolean isInAlertStatus;

	public TrainUserImpl(TrainController controller) {
		this.controller = controller;
		this.isInAlertStatus = false;
	}

	@Override
	public boolean getAlarmFlag() {
		return false;
	}

	@Override
	public int getJoystickPosition() {
		return joystickPosition;
	}
	
	@Override
	public void setEmergencyBreak(){
		this.controller.emergencyBreak();
	}

	@Override
	public void overrideJoystickPosition(int joystickPosition) {
		this.joystickPosition = joystickPosition;	
		controller.setJoystickPosition(this.joystickPosition); //using this.joystickPosition instead of parameter
	}

	@Override
	boolean getAlarmState() {
		return this.isInAlertStatus;
	}

	@Override
	void setAlarmState(boolean alarmState) {
		this.isInAlertStatus = alarmState;
	}

}
