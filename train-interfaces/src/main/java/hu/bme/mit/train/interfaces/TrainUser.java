package hu.bme.mit.train.interfaces;

public interface TrainUser {

	int getJoystickPosition();

	boolean getAlarmFlag();

	void setEmergencyBreak();

	void overrideJoystickPosition(int joystickPosition);

	boolean getAlarmState();

	void setAlarmState(boolean alarmState);
}
